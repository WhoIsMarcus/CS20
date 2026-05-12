package simulation;

import javax.swing.*;
import java.awt.*;

/**
 * PieChartPanel - Displays current population distribution by trait
 */
public class PieChartPanel extends JPanel {
    private SimulationEngine engine;
    private final Color speedColor = new Color(255, 223, 0);
    private final Color visionColor = new Color(220, 53, 69);
    private final Color resilienceColor = new Color(0, 123, 255);

    public PieChartPanel(SimulationEngine engine) {
        this.engine = engine;
        setBackground(new Color(15, 15, 20));
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        StatsTracker stats = engine.getStatsTracker();
        int speedCount = stats.getCurrentSpeedCount();
        int visionCount = stats.getCurrentVisionCount();
        int resilienceCount = stats.getCurrentResilienceCount();
        int total = speedCount + visionCount + resilienceCount;

        // Draw title
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2d.drawString("Population Distribution", 20, 30);

        if (total == 0) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g2d.drawString("No blobs in simulation", 20, 80);
            return;
        }

        // Draw pie chart
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2 + 20;
        int radius = 80;

        float speedPercentage = speedCount / (float) total;
        float visionPercentage = visionCount / (float) total;
        float resiliencePercentage = resilienceCount / (float) total;

        drawPieSlice(g2d, centerX, centerY, radius, 0, speedPercentage, speedColor);
        drawPieSlice(g2d, centerX, centerY, radius, speedPercentage, 
                    visionPercentage, visionColor);
        drawPieSlice(g2d, centerX, centerY, radius, speedPercentage + visionPercentage, 
                    resiliencePercentage, resilienceColor);

        // Draw legend with percentages
        int legendX = 20;
        int legendY = getHeight() - 80;

        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));

        g2d.setColor(speedColor);
        g2d.fillRect(legendX, legendY, 15, 15);
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.format("Speed: %d (%.1f%%)", speedCount, speedPercentage * 100), 
                      legendX + 20, legendY + 12);

        g2d.setColor(visionColor);
        g2d.fillRect(legendX, legendY + 20, 15, 15);
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.format("Vision: %d (%.1f%%)", visionCount, visionPercentage * 100), 
                      legendX + 20, legendY + 32);

        g2d.setColor(resilienceColor);
        g2d.fillRect(legendX, legendY + 40, 15, 15);
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.format("Resilience: %d (%.1f%%)", resilienceCount, resiliencePercentage * 100), 
                      legendX + 20, legendY + 52);

        g2d.setColor(Color.GRAY);
        g2d.drawString("Total: " + total, legendX, legendY - 10);
    }

    private void drawPieSlice(Graphics2D g, int centerX, int centerY, int radius, 
                             float startPercentage, float slicePercentage, Color color) {
        if (slicePercentage == 0) return;

        int startAngle = (int) (startPercentage * 360);
        int arcAngle = (int) (slicePercentage * 360);

        g.setColor(color);
        g.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, arcAngle);

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, arcAngle);
    }
}