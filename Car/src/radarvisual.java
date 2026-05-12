import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class radarvisual extends JPanel {

    static final int MAP_W = 700;
    static final int MAP_H = 600;

    static final Color BG = new Color(10, 12, 16);
    static final Color PANEL = new Color(16, 20, 28);
    static final Color GRID = new Color(25, 30, 40);

    static final int GRID_SIZE = 50;

    double heading = 0;
    double speed = 0.03;

    boolean drawMode = false;

    static class Wall {
        Line2D line;
        float glow = 0f;

        Wall(Line2D l) {
            this.line = l;
        }
    }

    List<Wall> walls = new ArrayList<>();

    Point start = null;
    Point mouse = null;

    public radarvisual() {

        setPreferredSize(new Dimension(MAP_W + 180, MAP_H));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!drawMode) return;
                start = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                if (!drawMode || start == null) return;
                walls.add(new Wall(new Line2D.Double(start, e.getPoint())));
                start = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouse = e.getPoint();
            }

            public void mouseDragged(MouseEvent e) {
                mouse = e.getPoint();
            }
        });

        JButton btn = new JButton("Start Draw Mode");
        btn.setBackground(new Color(50, 180, 100));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        btn.addActionListener(e -> {
            drawMode = !drawMode;
            btn.setText(drawMode ? "Stop Draw Mode" : "Start Draw Mode");
            btn.setBackground(drawMode ? new Color(200, 60, 60) : new Color(50, 180, 100));
        });

        JButton clear = new JButton("Clear Map");
        clear.addActionListener(e -> walls.clear());

        JFrame frame = new JFrame("SelfDrive Radar — Map Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(btn);
        top.add(clear);

        frame.add(top, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

        new Timer(16, e -> {
            heading += speed;
            updateRadarHits();
            repaint();
        }).start();
    }

    void updateRadarHits() {

        int cx = MAP_W / 2;
        int cy = MAP_H / 2;

        double dx = Math.cos(heading);
        double dy = Math.sin(heading);

        Line2D ray = new Line2D.Double(
                cx, cy,
                cx + dx * 1000,
                cy + dy * 1000
        );

        for (Wall w : walls) {
            if (ray.intersectsLine(w.line)) {
                w.glow = 1f;
            } else {
                w.glow *= 0.90f;
            }
        }
    }

    public void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int mapW = MAP_W;
        int mapH = MAP_H;

        g.setColor(BG);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(GRID);
        for (int x = 0; x < mapW; x += GRID_SIZE)
            g.drawLine(x, 0, x, mapH);
        for (int y = 0; y < mapH; y += GRID_SIZE)
            g.drawLine(0, y, mapW, y);

        int cx = mapW / 2;
        int cy = mapH / 2;

        g.setColor(new Color(0, 255, 160));
        g.fillOval(cx - 4, cy - 4, 8, 8);

        int r = 260;

        g.setStroke(new BasicStroke(2f));
        g.setColor(new Color(0, 255, 140, 160));

        int x2 = (int) (cx + Math.cos(heading) * r);
        int y2 = (int) (cy + Math.sin(heading) * r);

        g.drawLine(cx, cy, x2, y2);

        g.setColor(new Color(0, 255, 140, 25));
        g.fillOval(cx - r, cy - r, r * 2, r * 2);

        for (Wall w : walls) {

            Color base = new Color(80, 140, 255);
            Color hit = new Color(255, 80, 120);

            float t = w.glow;

            Color c = new Color(
                    (int) (base.getRed() * (1 - t) + hit.getRed() * t),
                    (int) (base.getGreen() * (1 - t) + hit.getGreen() * t),
                    (int) (base.getBlue() * (1 - t) + hit.getBlue() * t)
            );

            g.setStroke(new BasicStroke(3f + t * 2f));
            g.setColor(c);
            g.draw(w.line);

            if (t > 0.05f) {
                g.setColor(new Color(255, 255, 255, (int) (t * 100)));
                g.draw(w.line);
            }
        }

        if (drawMode && start != null && mouse != null) {
            g.setColor(new Color(255, 255, 255, 120));
            g.setStroke(new BasicStroke(2f));
            g.drawLine(start.x, start.y, mouse.x, mouse.y);
        }

        int hx = MAP_W + 8;

        g.setColor(PANEL);
        g.fillRect(MAP_W, 0, 180, MAP_H);

        g.setColor(new Color(40, 50, 68));
        g.drawLine(MAP_W, 0, MAP_W, MAP_H);

        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.setColor(new Color(255, 220, 50));
        g.drawString("RADAR HUD", hx + 18, 26);

        g.setColor(drawMode ? new Color(230, 80, 80) : new Color(50, 180, 100));
        g.fillRoundRect(hx, 40, 150, 20, 6, 6);

        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 11));
        g.drawString(drawMode ? "DRAW MODE" : "SCAN MODE", hx + 6, 54);

        g.setColor(new Color(200, 210, 225));
        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g.drawString("Walls: " + walls.size(), hx, 85);
        g.drawString("Angle: " + String.format("%.2f", heading), hx, 110);

        g.setColor(new Color(80, 140, 255));
        g.fillOval(hx, 140, 8, 8);
        g.setColor(new Color(200, 210, 225));
        g.drawString("Wall", hx + 14, 148);

        g.setColor(new Color(255, 80, 120));
        g.fillOval(hx, 165, 8, 8);
        g.drawString("Radar Hit", hx + 14, 173);

        g.setColor(new Color(0, 255, 160));
        g.fillOval(hx, 190, 8, 8);
        g.drawString("Radar Beam", hx + 14, 198);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(radarvisual::new);
    }
}