package simulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * GraphPanel - Line graph showing population trends
 */
public class GraphPanel extends JPanel {
    private SimulationEngine engine;
    private final Color speedColor = new Color(255, 223, 0);
    private final Color visionColor = new Color(220, 53, 69);
    private final Color resilienceColor = new Color(0, 123, 255);

    public GraphPanel(SimulationEngine engine) {
        this.engine = engine;
        setBackground(new Color(15, 15, 20));
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int padding = 50;
        int width = getWidth() - 2 * padding;
        int height = getHeight() - 2 * padding;

        // Draw background
        g2d.setColor(new Color(25, 25, 30));
        g2d.fillRect(padding, padding, width, height);

        // Draw axes
        drawAxes(g2d, padding, width, height);

        // Get data
        StatsTracker stats = engine.getStatsTracker();
        List<Integer> speedData = stats.getSpeedPopulation();
        List<Integer> visionData = stats.getVisionPopulation();
        List<Integer> resilienceData = stats.getResiliencePopulation();

        if (speedData.isEmpty()) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g2d.drawString("No data yet. Start simulation to see trends.", padding + 10, getHeight() / 2);
            return;
        }

        // Find max population for scaling
        int maxPop = 1;
        for (int pop : speedData) maxPop = Math.max(maxPop, pop);
        for (int pop : visionData) maxPop = Math.max(maxPop, pop);
        for (int pop : resilienceData) maxPop = Math.max(maxPop, pop);

        // Draw lines
        drawLine(g2d, speedData, speedColor, padding, height, width, maxPop);
        drawLine(g2d, visionData, visionColor, padding, height, width, maxPop);
        drawLine(g2d, resilienceData, resilienceColor, padding, height, width, maxPop);

        // Draw legend
        drawLegend(g2d, padding);
    }

    private void drawAxes(Graphics2D g, int padding, int width, int height) {
        g.setColor(new Color(100, 100, 110));
        g.setStroke(new BasicStroke(2));

        // Y-axis
        g.drawLine(padding, padding, padding, padding + height);
        // X-axis
        g.drawLine(padding, padding + height, padding + width, padding + height);

        // Labels
        g.setColor(Color.GRAY);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("Day", padding + width + 10, padding + height + 5);
        g.drawString("Population", 5, padding - 10);
    }

    private void drawLine(Graphics2D g, List<Integer> data, Color color, int padding, 
                         int height, int width, int maxPop) {
        if (data.size() < 2) return;

        g.setColor(color);
        g.setStroke(new BasicStroke(2));

        for (int i = 0; i < data.size() - 1; i++) {
            int x1 = padding + (i * width) / data.size();
            int y1 = padding + height - (int) ((data.get(i) / (float) maxPop) * height);

            int x2 = padding + ((i + 1) * width) / data.size();
            int y2 = padding + height - (int) ((data.get(i + 1) / (float) maxPop) * height);

            g.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawLegend(Graphics2D g, int padding) {
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        int x = padding;
        int y = 20;

        g.setColor(speedColor);
        g.fillRect(x, y, 12, 12);
        g.setColor(Color.WHITE);
        g.drawString("Speed", x + 18, y + 10);

        g.setColor(visionColor);
        g.fillRect(x + 100, y, 12, 12);
        g.setColor(Color.WHITE);
        g.drawString("Vision", x + 118, y + 10);

        g.setColor(resilienceColor);
        g.fillRect(x + 200, y, 12, 12);
        g.setColor(Color.WHITE);
        g.drawString("Resilience", x + 218, y + 10);
    }
}