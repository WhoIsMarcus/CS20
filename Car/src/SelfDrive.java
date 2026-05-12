import com.phidget22.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

public class SelfDrive extends JPanel {

    static final int    SCALE      = 3;
    static final int    DANGER_MM  = 200;
    static final int    CAUTION_MM = 500;
    static final double CRUISE     = 0.55;
    static final double REVERSE    = -0.40;
    static final double SPIN       = 0.35;
    static final int    MAP_W      = 700;
    static final int    MAP_H      = 600;

    static volatile int     front = 1000, back = 1000, left = 1000, right = 1000;
    static volatile boolean autoDrive = false;
    static volatile String  status    = "STOPPED";

    private final BufferedImage mapImg = new BufferedImage(MAP_W, MAP_H, BufferedImage.TYPE_INT_ARGB);
    private double roverX = MAP_W / 2.0, roverY = MAP_H / 2.0, heading = -Math.PI / 2;
    private final Deque<Point2D.Double> trail = new ArrayDeque<>();

    public static void main(String[] args) throws Exception {
        Net.addServer("", "192.168.100.1", 5661, "", 0);

        DistanceSensor fs = new DistanceSensor(), bs = new DistanceSensor(),
                       ls = new DistanceSensor(), rs = new DistanceSensor();
        fs.setHubPort(4); bs.setHubPort(3); ls.setHubPort(1); rs.setHubPort(5);
        fs.open(5000); bs.open(5000); ls.open(5000); rs.open(5000);

        DCMotor lm = new DCMotor(), rm = new DCMotor();
        VoltageRatioInput joy = new VoltageRatioInput();
        lm.setChannel(0); rm.setChannel(1); joy.setChannel(0);
        lm.open(5000); rm.open(5000); joy.open(5000);
        lm.setAcceleration(lm.getMaxAcceleration());
        rm.setAcceleration(rm.getMaxAcceleration());

        SelfDrive panel = new SelfDrive();

        JButton btn = new JButton("Start Auto-Drive");
        btn.setBackground(new Color(50, 180, 100));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.addActionListener(e -> {
            autoDrive = !autoDrive;
            if (autoDrive) {
                btn.setText("Stop Auto-Drive");
                btn.setBackground(new Color(200, 60, 60));
                status = "CRUISING";
            } else {
                btn.setText("Start Auto-Drive");
                btn.setBackground(new Color(50, 180, 100));
                status = "STOPPED";
            }
        });

        JFrame frame = new JFrame("Rover — Map View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(btn, BorderLayout.SOUTH);
        frame.setSize(MAP_W + 180, MAP_H + 50);
        frame.setVisible(true);

        while (true) {
            front = clamp(fs.getDistance(), 0, 3000);
            back  = clamp(bs.getDistance(), 0, 3000);
            left  = clamp(ls.getDistance(), 0, 3000);
            right = clamp(rs.getDistance(), 0, 3000);

            double joyY   = joy.getVoltageRatio() / 2.0;
            boolean manual = Math.abs(joyY) > 0.05;
            double lv = 0, rv = 0;

            if (manual) {
                status = "MANUAL";
                lv = rv = joyY;
            } else if (autoDrive) {
                if (front < DANGER_MM) {
                    status = "REVERSING";
                    double bias = right > left ? 1 : -1;
                    lv = REVERSE - bias * SPIN;
                    rv = REVERSE + bias * SPIN;
                } else if (front < CAUTION_MM) {
                    status = "STEERING";
                    double bias = SPIN * (1.0 - (double) front / CAUTION_MM);
                    lv = right > left ? CRUISE + bias : CRUISE - bias;
                    rv = right > left ? CRUISE - bias : CRUISE + bias;
                } else {
                    status = "CRUISING";
                    lv = rv = CRUISE;
                }
            }

            lm.setTargetVelocity(Math.max(-1, Math.min(1, lv)));
            rm.setTargetVelocity(Math.max(-1, Math.min(1, rv)));

            // Dead-reckoning position update
            double fwd = (lv + rv) / 2.0;
            panel.heading += (rv - lv) * 0.05;
            panel.roverX   = clamp(panel.roverX + Math.cos(panel.heading) * fwd * 4, 10, MAP_W - 10);
            panel.roverY   = clamp(panel.roverY + Math.sin(panel.heading) * fwd * 4, 10, MAP_H - 10);

            panel.trail.addLast(new Point2D.Double(panel.roverX, panel.roverY));
            if (panel.trail.size() > 250) panel.trail.removeFirst();
            panel.stampWalls();
            panel.repaint();
            Thread.sleep(80);
        }
    }

    static int    clamp(int v, int lo, int hi)       { return Math.max(lo, Math.min(hi, v)); }
    static double clamp(double v, double lo, double hi) { return Math.max(lo, Math.min(hi, v)); }

    void stampWalls() {
        Graphics2D mg = mapImg.createGraphics();
        int rx = (int) roverX, ry = (int) roverY;
        int[]    dists = { front, back, left, right };
        double[] angs  = { heading, heading + Math.PI, heading - Math.PI/2, heading + Math.PI/2 };
        Color[]  cols  = {
            new Color(220, 80,  60,  160),
            new Color( 60, 130, 220, 160),
            new Color( 60, 200, 120, 160),
            new Color(200,  80, 200, 160)
        };
        for (int i = 0; i < 4; i++) {
            int wx = (int)(rx + Math.cos(angs[i]) * dists[i] / SCALE);
            int wy = (int)(ry + Math.sin(angs[i]) * dists[i] / SCALE);
            if (wx < 0 || wx >= MAP_W || wy < 0 || wy >= MAP_H) continue;
            mg.setColor(new Color(cols[i].getRed(), cols[i].getGreen(), cols[i].getBlue(), 18));
            mg.drawLine(rx, ry, wx, wy);
            mg.setColor(cols[i]);
            mg.fillOval(wx - 3, wy - 3, 7, 7);
        }
        mg.dispose();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int W = getWidth(), H = getHeight();
        int mapW = W - 170;

        // Background + grid
        g.setColor(new Color(10, 12, 16));
        g.fillRect(0, 0, W, H);
        g.setColor(new Color(25, 30, 40));
        g.setStroke(new BasicStroke(1));
        for (int x = 0; x < mapW; x += 50) g.drawLine(x, 0, x, H);
        for (int y = 0; y < H;    y += 50) g.drawLine(0, y, mapW, y);

        // Persistent map
        g.setClip(0, 0, mapW, H);
        g.drawImage(mapImg, 0, 0, null);
        g.setClip(null);

        // Trail
        Point2D.Double[] pts = trail.toArray(new Point2D.Double[0]);
        for (int i = 1; i < pts.length; i++) {
            g.setColor(new Color(1f, 0.86f, 0.2f, (float) i / pts.length * 0.65f));
            g.setStroke(new BasicStroke(1.8f));
            g.drawLine((int)pts[i-1].x, (int)pts[i-1].y, (int)pts[i].x, (int)pts[i].y);
        }

        // Sonar rays + wall markers
        int rx = (int) roverX, ry = (int) roverY;
        int[]    dists = { front, back, left, right };
        double[] angs  = { heading, heading + Math.PI, heading - Math.PI/2, heading + Math.PI/2 };
        Color[]  cols  = {
            new Color(230, 100, 80), new Color(80, 150, 230),
            new Color(80, 210, 130), new Color(210, 90, 210)
        };
        String[] lbls = { "F", "B", "L", "R" };

        for (int i = 0; i < 4; i++) {
            int wx = clamp((int)(rx + Math.cos(angs[i]) * dists[i] / SCALE), 0, mapW - 1);
            int wy = clamp((int)(ry + Math.sin(angs[i]) * dists[i] / SCALE), 0, H - 1);
            g.setColor(new Color(cols[i].getRed(), cols[i].getGreen(), cols[i].getBlue(), 60));
            g.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{5, 5}, 0f));
            g.drawLine(rx, ry, wx, wy);
            g.setColor(cols[i]);
            g.setStroke(new BasicStroke(1.5f));
            g.fillOval(wx - 4, wy - 4, 9, 9);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 11));
            g.drawString(lbls[i] + ":" + dists[i], wx + 7, wy + 4);
        }

        // Rover sprite
        AffineTransform saved = g.getTransform();
        g.translate(rx, ry);
        g.rotate(heading + Math.PI / 2);
        g.setColor(new Color(70, 72, 82));
        int[][] wheels = {{-17,-13},{12,-13},{-17,4},{12,4}};
        for (int[] w : wheels) g.fillRoundRect(w[0], w[1], 5, 11, 2, 2);
        g.setColor(new Color(255, 220, 50));
        g.fillRoundRect(-11, -15, 22, 30, 5, 5);
        g.setColor(new Color(190, 150, 0));
        g.setStroke(new BasicStroke(1.5f));
        g.drawRoundRect(-11, -15, 22, 30, 5, 5);
        g.setColor(new Color(10, 12, 16));
        g.fillPolygon(new int[]{0, -4, 4}, new int[]{-18, -11, -11}, 3);
        g.setTransform(saved);

        // HUD panel
        int hx = mapW + 8;
        g.setColor(new Color(16, 20, 28));
        g.fillRect(mapW, 0, 170, H);
        g.setColor(new Color(40, 50, 68));
        g.setStroke(new BasicStroke(1f));
        g.drawLine(mapW, 0, mapW, H);

        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.setColor(new Color(255, 220, 50));
        g.drawString("ROVER HUD", hx + 18, 26);

        // Status badge
        Color sc = switch (status) {
            case "CRUISING"  -> new Color(50, 190, 100);
            case "STEERING"  -> new Color(230, 170, 40);
            case "REVERSING" -> new Color(220, 65,  55);
            case "MANUAL"    -> new Color(70, 150, 220);
            default          -> new Color(80, 88, 105);
        };
        g.setColor(sc);
        g.fillRoundRect(hx, 36, 155, 20, 6, 6);
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 11));
        g.drawString(status, hx + 6, 50);

        // Sensor bars
        String[] names = { "FRONT", "BACK", "LEFT", "RIGHT" };
        int[] readings  = { front, back, left, right };
        int hy = 72;
        for (int i = 0; i < 4; i++) {
            g.setColor(new Color(130, 140, 160));
            g.setFont(new Font("Monospaced", Font.PLAIN, 11));
            g.drawString(names[i], hx, hy + 11);
            int bw = Math.min(100, readings[i] / 28);
            g.setColor(new Color(28, 34, 46));
            g.fillRoundRect(hx + 42, hy, 100, 13, 3, 3);
            g.setColor(cols[i]);
            g.fillRoundRect(hx + 42, hy, bw, 13, 3, 3);
            g.setColor(new Color(200, 210, 225));
            g.setFont(new Font("Monospaced", Font.PLAIN, 10));
            g.drawString(readings[i] + "mm", hx + 144, hy + 10);
            hy += 22;
        }

        hy += 4;
        g.setColor(new Color(40, 50, 68));
        g.drawLine(hx, hy, hx + 155, hy);
        hy += 12;

        // Threshold legend
        g.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g.setColor(new Color(220, 65, 55));  g.fillOval(hx, hy, 8, 8);
        g.setColor(new Color(170, 180, 200)); g.drawString("Danger  <" + DANGER_MM  + "mm", hx + 13, hy + 9); hy += 16;
        g.setColor(new Color(230, 170, 40)); g.fillOval(hx, hy, 8, 8);
        g.setColor(new Color(170, 180, 200)); g.drawString("Caution <" + CAUTION_MM + "mm", hx + 13, hy + 9); hy += 22;

        // Map legend
        g.setColor(new Color(90, 100, 120));
        g.drawString("Map legend:", hx, hy); hy += 14;
        String[] lnames = { "Front", "Back", "Left", "Right" };
        for (int i = 0; i < 4; i++) {
            g.setColor(cols[i]); g.fillOval(hx, hy, 8, 8);
            g.setColor(new Color(170, 180, 200)); g.drawString(lnames[i], hx + 14, hy + 9);
            hy += 16;
        }
        hy += 4;
        g.setColor(new Color(255, 220, 50, 180));
        g.setStroke(new BasicStroke(2f));
        g.drawLine(hx, hy + 4, hx + 18, hy + 4);
        g.setColor(new Color(170, 180, 200));
        g.drawString("Trail", hx + 24, hy + 9);
    }
}