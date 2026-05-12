// Add Phidgets Library
import com.phidget22.*;

public class tilt {
    public static void main(String[] args) throws Exception {

        // Connect 
        Net.addServer("", "192.168.100.1", 5661, "", 0);

        // Create
        Gyroscope gyro = new Gyroscope();
        
      
        // Open
        gyro.open(5000);

        // Rot ang
        double rotX = 0;
        double rotY = 0;
        double rotZ = 0;

        // Time
        long lastTime = System.currentTimeMillis();

        while (true) {

            // Current time
            long currentTime = System.currentTimeMillis();
            double dt = (currentTime - lastTime) / 1000.0; // seconds
            lastTime = currentTime;

            // Get angular
            double xRate = gyro.getAngularRate()[0];
            double yRate = gyro.getAngularRate()[1];
            double zRate = gyro.getAngularRate()[2];

            // Integrate
            rotX += xRate * dt;
            rotY += yRate * dt;
            rotZ += zRate * dt;

            // Print rotation
            System.out.println(
                "X Rotation: " + rotX +
                "° | Y Rotation: " + rotY +
                "° | Z Rotation: " + rotZ + "°"
            );

            Thread.sleep(50);
        }
    }
}