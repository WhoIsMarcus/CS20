package simulation;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

/**
 * ControlPanel - Provides UI controls for simulation management
 */
public class ControlPanel extends JPanel {
    private SimulationEngine engine;
    private SimulationPanel simPanel;
    private GraphPanel graphPanel;
    private PieChartPanel piePanel;
    
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JSlider speedSlider;
    private JSlider gridSizeSlider;
    private JSpinner speedBlobSpinner;
    private JSpinner visionBlobSpinner;
    private JSpinner resilienceBlobSpinner;
    private JSpinner foodDensitySpinner;
    private JLabel statusLabel;

    public ControlPanel(SimulationEngine engine, SimulationPanel simPanel, 
                       GraphPanel graphPanel, PieChartPanel piePanel) {
        this.engine = engine;
        this.simPanel = simPanel;
        this.graphPanel = graphPanel;
        this.piePanel = piePanel;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(20, 20, 25));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel titleLabel = new JLabel("🧬 Simulation Controls");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);
        add(Box.createVerticalStrut(15));

        // Simulation controls section
        add(createSimulationControlsSection());
        add(Box.createVerticalStrut(15));

        // Initial population section
        add(createInitialPopulationSection());
        add(Box.createVerticalStrut(15));

        // Settings section
        add(createSettingsSection());
        add(Box.createVerticalGlue());

        // Status
        statusLabel = new JLabel("Status: Ready");
        statusLabel.setForeground(new Color(100, 200, 100));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        add(statusLabel);
    }

    private JPanel createSimulationControlsSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(new Color(25, 25, 30));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 70)),
            "Simulation",
            1, 2,
            new Font("SansSerif", Font.BOLD, 12),
            Color.WHITE
        ));

        startButton = new JButton("▶ Start");
        startButton.setBackground(new Color(0, 150, 0));
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        startButton.addActionListener(e -> {
            engine.start();
            updateStatus("Running", new Color(100, 200, 100));
        });
        panel.add(startButton);

        pauseButton = new JButton("⏸ Pause");
        pauseButton.setBackground(new Color(150, 100, 0));
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        pauseButton.addActionListener(e -> {
            engine.stop();
            updateStatus("Paused", new Color(200, 150, 0));
        });
        panel.add(pauseButton);

        resetButton = new JButton("↻ Reset");
        resetButton.setBackground(new Color(150, 0, 0));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        resetButton.addActionListener(e -> resetSimulation());
        panel.add(resetButton);

        return panel;
    }

    private JPanel createInitialPopulationSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(25, 25, 30));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 70)),
            "Initial Population",
            1, 2,
            new Font("SansSerif", Font.BOLD, 12),
            Color.WHITE
        ));

        // Speed blobs
        JLabel speedLabel = new JLabel("🟡 Speed Blobs:");
        speedLabel.setForeground(new Color(255, 223, 0));
        speedLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        speedBlobSpinner = new JSpinner(new SpinnerNumberModel(20, 0, 100, 5));
        styleSpinner(speedBlobSpinner);
        panel.add(speedLabel);
        panel.add(speedBlobSpinner);

        // Vision blobs
        JLabel visionLabel = new JLabel("🔴 Vision Blobs:");
        visionLabel.setForeground(new Color(220, 53, 69));
        visionLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        visionBlobSpinner = new JSpinner(new SpinnerNumberModel(20, 0, 100, 5));
        styleSpinner(visionBlobSpinner);
        panel.add(visionLabel);
        panel.add(visionBlobSpinner);

        // Resilience blobs
        JLabel resilienceLabel = new JLabel("🔵 Resilience Blobs:");
        resilienceLabel.setForeground(new Color(0, 123, 255));
        resilienceLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        resilienceBlobSpinner = new JSpinner(new SpinnerNumberModel(20, 0, 100, 5));
        styleSpinner(resilienceBlobSpinner);
        panel.add(resilienceLabel);
        panel.add(resilienceBlobSpinner);

        return panel;
    }

    private JPanel createSettingsSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(25, 25, 30));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 70)),
            "Settings",
            1, 2,
            new Font("SansSerif", Font.BOLD, 12),
            Color.WHITE
        ));

        // Speed slider
        JLabel speedSliderLabel = new JLabel("Simulation Speed: 1x");
        speedSliderLabel.setForeground(Color.WHITE);
        speedSliderLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        speedSlider = new JSlider(1, 100, 1);
        speedSlider.setBackground(new Color(25, 25, 30));
        speedSlider.setForeground(Color.WHITE);
        speedSlider.addChangeListener(e -> {
            speedSliderLabel.setText("Simulation Speed: " + speedSlider.getValue() + "x");
        });
        panel.add(speedSliderLabel);
        panel.add(speedSlider);

        panel.add(Box.createVerticalStrut(10));

        // Food density
        JLabel foodLabel = new JLabel("Food Density: 5%");
        foodLabel.setForeground(Color.WHITE);
        foodLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        foodDensitySpinner = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));
        styleSpinner(foodDensitySpinner);
        foodDensitySpinner.addChangeListener(e -> {
            foodLabel.setText("Food Density: " + foodDensitySpinner.getValue() + "%");
        });
        panel.add(foodLabel);
        panel.add(foodDensitySpinner);

        return panel;
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setBackground(new Color(35, 35, 40));
        ((JComponent) spinner.getEditor()).setForeground(Color.WHITE);
        ((JComponent) spinner.getEditor()).setBackground(new Color(35, 35, 40));
    }

    private void resetSimulation() {
        int speedCount = (int) speedBlobSpinner.getValue();
        int visionCount = (int) visionBlobSpinner.getValue();
        int resilienceCount = (int) resilienceBlobSpinner.getValue();
        
        engine.reset(speedCount, visionCount, resilienceCount);
        simPanel.repaint();
        graphPanel.repaint();
        piePanel.repaint();
        updateStatus("Reset complete", new Color(100, 150, 200));
    }

    private void updateStatus(String text, Color color) {
        statusLabel.setText("Status: " + text);
        statusLabel.setForeground(color);
    }

    public int getSimulationSpeed() {
        return speedSlider.getValue();
    }

    public float getFoodDensity() {
        return (Integer) foodDensitySpinner.getValue();
    }
}