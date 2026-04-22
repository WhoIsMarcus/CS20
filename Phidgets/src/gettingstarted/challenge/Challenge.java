package gettingstarted.challenge;

import gettingstarted.PhidgetManager;

public class Challenge {

    public static void run() throws Exception {

        System.out.println("\n=== CHALLENGE: THERMOSTAT ===");

        double setTemp = 21;

        boolean lastR = false;
        boolean lastG = false;

        while (true) {

            double t = PhidgetManager.tempSensor.getTemperature();

            boolean r = PhidgetManager.redButton.getState();
            boolean g = PhidgetManager.greenButton.getState();

            if (r && !lastR) setTemp--;
            if (g && !lastG) setTemp++;

            lastR = r;
            lastG = g;

            if (Math.abs(t - setTemp) <= 2) {
                PhidgetManager.greenLED.setState(true);
                PhidgetManager.redLED.setState(false);
            } else {
                PhidgetManager.greenLED.setState(false);
                PhidgetManager.redLED.setState(true);
            }

            System.out.println("Temp: " + t + " Set: " + setTemp);

            Thread.sleep(150);
        }
    }
}