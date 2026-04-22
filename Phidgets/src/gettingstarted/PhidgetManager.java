package gettingstarted;

import com.phidget22.*;

public class PhidgetManager {

    // Declare the variables here
    public static DigitalInput redButton;
    public static DigitalInput greenButton;
    public static DigitalOutput redLED;
    public static DigitalOutput greenLED;
    public static TemperatureSensor tempSensor;
    public static HumiditySensor humiditySensor;

    public static void init() throws Exception {
        
        // Initialize the objects here (inside the method)
        redButton = new DigitalInput();
        greenButton = new DigitalInput();
        redLED = new DigitalOutput();
        greenLED = new DigitalOutput();
        tempSensor = new TemperatureSensor();
        humiditySensor = new HumiditySensor();

        redButton.setHubPort(0);
        redButton.setIsHubPortDevice(true);

        greenButton.setHubPort(5);
        greenButton.setIsHubPortDevice(true);

        redLED.setHubPort(1);
        redLED.setIsHubPortDevice(true);

        greenLED.setHubPort(4);
        greenLED.setIsHubPortDevice(true);

        redButton.open(1000);
        greenButton.open(1000);
        redLED.open(1000);
        greenLED.open(1000);

        tempSensor.open(1000);
        humiditySensor.open(1000);
    }
}
