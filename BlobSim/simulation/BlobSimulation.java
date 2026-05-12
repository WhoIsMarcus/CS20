package simulation;

import javax.swing.*;

public class BlobSimulation {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Blob Evolution Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);
            
            SimulationController controller = new SimulationController();
            frame.setContentPane(controller.getMainPanel());
            
            frame.setVisible(true);
        });
    }
}