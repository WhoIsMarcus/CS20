import com.phidget22.*;

public class gyrotest {
    public static void main(String[] args) throws Exception {

        Spatial spatial = new Spatial();

        spatial.addAttachListener(e -> {
            System.out.println("✔ Device ATTACHED");
        });

        spatial.addDetachListener(e -> {
            System.out.println("✖ Device DETACHED");
        });

        spatial.addErrorListener(e -> {
            System.out.println("ERROR: " + e.getDescription());
        });

        spatial.open(5000);

        System.out.println("Waiting for attachment...");
        System.out.println("Spatial opened");

        System.out.println("DONE - running");
    }
}