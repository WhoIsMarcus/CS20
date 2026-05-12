

package gettingstarted.smart;

import gettingstarted.PhidgetManager;

public class SmartPhidgets {

    static int option = 0;
    static boolean lastR = false;
    static boolean lastG = false;

    public static void run() throws Exception {

        System.out.println("\n=== SMART PHIDGETS ===");

        showMenu();

        while (true) {

            boolean r = PhidgetManager.redButton.getState();
            boolean g = PhidgetManager.greenButton.getState();

            boolean rPress = r && !lastR;
            boolean gPress = g && !lastG;

            lastR = r;
            lastG = g;

        
            if (rPress) {
                option = (option + 1) % 3;
                showMenu();
            }

           
            if (gPress) {
                runLesson(option);
                showMenu(); // return after finishing
            }

            
            if (r && g) return;

            Thread.sleep(150);
        }
    }

    static void showMenu() {
        System.out.println("\n--- SMART MENU ---");
        System.out.println("0 Temperature");
        System.out.println("1 Temperature + Humidity");
        System.out.println("2 Hot or Cold");
        System.out.println("Selected: " + option);
    }

    static void runLesson(int o) throws Exception {

        System.out.println("\nRunning lesson " + o + "...\n");

        switch (o) {

            case 0 -> temperature();
            case 1 -> tempHumidity();
            case 2 -> hotOrCold();
        }
    }

    // ================= LESSON 4 =================
    static void temperature() throws Exception {

        for (int i = 0; i < 30; i++) {

            double t = PhidgetManager.tempSensor.getTemperature();
            System.out.println("Temp: " + t);

            Thread.sleep(200);
        }
    }

    // ================= LESSON 5 =================
    static void tempHumidity() throws Exception {

        for (int i = 0; i < 30; i++) {

            double t = PhidgetManager.tempSensor.getTemperature();
            double h = PhidgetManager.humiditySensor.getHumidity();

            System.out.println("Temp: " + t + " | Humidity: " + h);

            Thread.sleep(200);
        }
    }

    // ================= PRACTICE =================
    static void hotOrCold() throws Exception {

        for (int i = 0; i < 100; i++) {

            double t = PhidgetManager.tempSensor.getTemperature();

            if (t >= 20 && t <= 24) {
                PhidgetManager.greenLED.setState(true);
                PhidgetManager.redLED.setState(false);
            } else {
                PhidgetManager.greenLED.setState(false);
                PhidgetManager.redLED.setState(true);
            }

            System.out.println("Temp: " + t);

            Thread.sleep(150);
        }
    }
}