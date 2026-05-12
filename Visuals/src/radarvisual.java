import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class radarvisual extends JPanel {

    // ===================== SETTINGS =====================
    static final int W = 900;
    static final int H = 650;

    static final Color BG = new Color(10, 12, 18);
    static final Color GRID = new Color(25, 30, 45);

    static final int GRID_SIZE = 40;

    // ===================== STATE =====================
    boolean drawMode = false;

    double radarAngle = 0;
    double radarSpeed = 0.03;

    List<Wall> walls = new ArrayList<>();

    Point startPoint = null;
    Point currentMouse = null;

    // ===================== WALL =====================
    static class Wall {
        Line2D line;
        float glow = 0f;

        Wall(Line2D l) {
            this.line = l;
        }
    }

    // ===================== INIT =====================
    public RadarSim() {

        setPreferredSize(new Dimension(W, H));
        setBackground(BG);

        // Mouse drawing
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!drawMode) return;
                startPoint = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                if (!drawMode || startPoint == null) return;

                Point end = e.getPoint();
                walls.add(new Wall(new Line2D.Double(startPoint, end)));

                startPoint = null;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                currentMouse = e.getPoint();
            }

            public void mouseDragged(MouseEvent e) {
                currentMouse = e.getPoint();
                repaint();
            }
        });

        // UI
        JButton toggleDraw = new JButton("Draw Mode: OFF");
        JButton clear = new JButton("Clear");

        toggleDraw.addActionListener(e -> {
            drawMode = !drawMode;
            toggleDraw.setText(drawMode ? "Draw Mode: ON" : "Draw Mode: OFF");
        });

        clear.addActionListener(e -> walls.clear());

        JFrame frame = new JFrame("Radar Map Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(toggleDraw);
        top.add(clear);

        frame.add(top, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

        // animation loop
        new Timer(16, e -> {
            radarAngle += radarSpeed;
            updateRadarHits();
            repaint();
        }).start();
    }

    // ===================== RADAR HIT LOGIC =====================
    void updateRadarHits() {

        Point center = new Point(W / 2, H / 2);

        double rayX = Math.cos(radarAngle);
        double rayY = Math.sin(radarAngle);

        Line2D radarRay = new Line2D.Double(
                center.x, center.y,
                center.x + rayX * 1000,
                center.y + rayY * 1000
        );

        for (Wall w : walls) {
            if (radarRay.intersectsLine(w.line)) {
                w.glow = 1f;
            } else {
                w.glow *= 0.92f;
            }
        }
    }

    // ===================== DRAW =====================
    public void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = W / 2;
        int centerY = H / 2;

        // ================= BACKGROUND =================
        g.setColor(BG);
        g.fillRect(0, 0, W, H);

        g.setColor(GRID);
        for (int x = 0; x < W; x += GRID_SIZE)
            g.drawLine(x, 0, x, H);
        for (int y = 0; y < H; y += GRID_SIZE)
            g.drawLine(0, y, W, y);

        // ================= RADAR CENTER =================
        g.setColor(new Color(0, 255, 160));
        g.fillOval(centerX - 5, centerY - 5, 10, 10);

        // ================= RADAR SWEEP =================
        int radius = 300;

        GradientPaint sweepGlow = new GradientPaint(
                centerX, centerY,
                new Color(0, 255, 140, 180),
                centerX + (int) (Math.cos(radarAngle) * radius),
                centerY + (int) (Math.sin(radarAngle) * radius),
                new Color(0, 255, 140, 0)
        );

        g.setPaint(sweepGlow);
        g.setStroke(new BasicStroke(2f));

        g.drawLine(
                centerX, centerY,
                (int) (centerX + Math.cos(radarAngle) * radius),
                (int) (centerY + Math.sin(radarAngle) * radius)
        );

        // radar arc glow
        g.setColor(new Color(0, 255, 140, 30));
        g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        // ================= WALLS =================
        for (Wall w : walls) {

            float glow = w.glow;

            Color base = new Color(80, 140, 255);
            Color hit = new Color(255, 80, 120);

            Color c = blend(base, hit, glow);

            g.setColor(c);
            g.setStroke(new BasicStroke(3f + glow * 2));

            g.draw(w.line);

            // glow core
            if (glow > 0.05f) {
                g.setColor(new Color(255, 255, 255, (int) (glow * 120)));
                g.draw(w.line);
            }
        }

        // ================= DRAW PREVIEW =================
        if (drawMode && startPoint != null && currentMouse != null) {
            g.setColor(new Color(255, 255, 255, 120));
            g.setStroke(new BasicStroke(2f));
            g.drawLine(
                    startPoint.x, startPoint.y,
                    currentMouse.x, currentMouse.y
            );
        }

        // ================= HUD =================
        g.setColor(new Color(15, 18, 28, 220));
        g.fillRect(10, 10, 220, 90);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        g.drawString("RADAR SIM", 20, 35);

        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g.drawString("Mode: " + (drawMode ? "DRAW" : "SCAN"), 20, 60);
        g.drawString("Walls: " + walls.size(), 20, 80);
    }

    // ===================== COLOR BLEND =====================
    Color blend(Color a, Color b, float t) {
        t = Math.min(1, Math.max(0, t));
        return new Color(
                (int) (a.getRed() * (1 - t) + b.getRed() * t),
                (int) (a.getGreen() * (1 - t) + b.getGreen() * t),
                (int) (a.getBlue() * (1 - t) + b.getBlue() * t)
        );
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RadarSim::new);
    }
}