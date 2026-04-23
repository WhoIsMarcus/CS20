package gettingstarted;

import gettingstarted.simple.SimplePhidgets;
import gettingstarted.smart.SmartPhidgets;
import gettingstarted.challenge.Challenge;
import gettingstarted.advanced.AdvancedPhidgets;

public class GettingStarted {

    static int menu = 0;

    static boolean lastRed = false;
    static boolean lastGreen = false;

    public static void main(String[] args) throws Exception {

        PhidgetManager.init();

        System.out.println("PHIDGET LEARNING SYSTEM");
        showMenu();

        while (true) {

            boolean red = PhidgetManager.redButton.getState();
            boolean green = PhidgetManager.greenButton.getState();

            boolean redPress = red && !lastRed;
            boolean greenPress = green && !lastGreen;

            lastRed = red;
            lastGreen = green;

            //  Cycle menu
            if (redPress) {
                menu = (menu + 1) % 4;
                showMenu();
            }

            //  Select menu
            if (greenPress) {
                run(menu);
                showMenu();
            }

            Thread.sleep(150);
        }
    }

    // ================= MENU =================

    static void showMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("0 SIMPLE PHIDGETS");
        System.out.println("1 SMART PHIDGETS");
        System.out.println("2 CHALLENGE");
        System.out.println("3 ADVANCED PHIDGETS");
        System.out.println("SELECTED: " + menu);
    }

    // ================= RUN SELECTED MODE =================

    static void run(int m) throws Exception {

        switch (m) {

            case 0 -> SimplePhidgets.run();
            case 1 -> SmartPhidgets.run();
            case 2 -> Challenge.run();
            case 3 -> AdvancedPhidgets.run(); // ✅ FIXED ADVANCED CALL
        }
    }
}