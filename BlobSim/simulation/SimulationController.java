

import javax.swing.*;
import java.awt.*;

/**
 * SimulationController - Manages the overall application and simulation loop
 */
public class SimulationController {
    private SimulationEngine engine;
    private JPanel mainPanel;
    private SimulationPanel simPanel;
    private GraphPanel graphPanel;
    private PieChartPanel piePanel;
    private ControlPanel controlPanel;
    private Timer simulationTimer;

    public SimulationController() {
        // Initialize engine with default settings
        engine = new SimulationEngine(80, 80, 5.0f, 0.1f);
        engine.initializeBlobs(20, 20, 20);

        // Create main layout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(15, 15, 20));

        // Create visualization panels
        simPanel = new SimulationPanel(engine);
        graphPanel = new GraphPanel(engine);
        piePanel = new PieChartPanel(engine);
        controlPanel = new ControlPanel(engine, simPanel, graphPanel, piePanel);

        // Build layout
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.setBackground(new Color(15, 15, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        centerPanel.add(simPanel);
        centerPanel.add(piePanel);
        centerPanel.add(graphPanel);
        
        // Stats panel
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(new Color(20, 20, 25));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(statsPanel);

        // Add to main panel
        mainPanel.add(controlPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Start simulation loop
        startSimulationLoop();
    }

    /**
     * Start the main simulation loop
     */
    private void startSimulationLoop() {
        simulationTimer = new Timer(50, e -> {
            if (engine.isRunning()) {
                int speed = controlPanel.getSimulationSpeed();
                
                // Run multiple steps per frame based on speed slider
                for (int i = 0; i < speed; i++) {
                    engine.simulateDay();
                }
                
                // Reset food counters for next day
                for (Blob blob : engine.getBlobs()) {
                    blob.resetFoodCounter();
                }
            }

            // Repaint visualization panels
            simPanel.repaint();
            graphPanel.repaint();
            piePanel.repaint();
        });
        simulationTimer.start();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}