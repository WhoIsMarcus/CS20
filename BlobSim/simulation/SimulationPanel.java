package simulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * SimulationPanel - Renders the world, blobs, and food
 */
public class SimulationPanel extends JPanel {
    private SimulationEngine engine;
    private int cellSize;

    public SimulationPanel(SimulationEngine engine) {
        this.engine = engine;
        this.cellSize = 12;
        setBackground(new Color(15, 15, 20));
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw grid
        drawGrid(g2d);

        // Draw food
        drawFood(g2d);

        // Draw blobs
        drawBlobs(g2d);

        // Draw info
        drawInfo(g2d);
    }

    private void drawGrid(Graphics2D g) {
        g.setColor(new Color(40, 40, 50));
        WorldGrid world = engine.getWorld();
        
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                int screenX = x * cellSize;
                int screenY = y * cellSize;
                g.drawRect(screenX, screenY, cellSize, cellSize);
            }
        }
    }

    private void drawFood(Graphics2D g) {
        g.setColor(new Color(100, 200, 100));
        FoodSystem foodSystem = engine.getFoodSystem();
        
        for (int[] food : foodSystem.getFoodPositions()) {
            int screenX = food[0] * cellSize + cellSize / 4;
            int screenY = food[1] * cellSize + cellSize / 4;
            int size = cellSize / 2;
            g.fillOval(screenX, screenY, size, size);
        }
    }

    private void drawBlobs(Graphics2D g) {
        for (Blob blob : engine.getBlobs()) {
            int screenX = blob.getX() * cellSize;
            int screenY = blob.getY() * cellSize;

            // Draw blob with trait color
            g.setColor(blob.getTraitType().getColor());
            g.fillOval(screenX + 1, screenY + 1, cellSize - 2, cellSize - 2);

            // Draw border
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(1));
            g.drawOval(screenX + 1, screenY + 1, cellSize - 2, cellSize - 2);
        }
    }

    private void drawInfo(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        int y = 20;
        g.drawString("Day: " + engine.getCurrentDay(), 10, y);
        g.drawString("Population: " + engine.getBlobs().size(), 10, y + 25);
        g.drawString("Food: " + engine.getFoodSystem().getFoodCount(), 10, y + 50);
        g.drawString(engine.isRunning() ? "RUNNING" : "PAUSED", 10, y + 75);
    }

    public void setCellSize(int size) {
        this.cellSize = size;
        repaint();
    }
}