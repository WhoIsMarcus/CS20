/*

Program: SimplePhidgets.java          Last Date of this Revision: March 31, 2026

Purpose: this is all of the simple phidgets projects in one simple file so you can choose which one to try

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/


package gettingstarted.simple;

import gettingstarted.PhidgetManager;

public class SimplePhidgets {

    public static void run() throws Exception {

        int option = 0;
        boolean lastR = false;
        boolean lastG = false;

        System.out.println("\n=== SIMPLE PHIDGETS ===");

        showMenu(option);

        while (true) {

            boolean r = PhidgetManager.redButton.getState();
            boolean g = PhidgetManager.greenButton.getState();

            boolean rPress = r && !lastR;
            boolean gPress = g && !lastG;

            lastR = r;
            lastG = g;

            // cycle options
            if (rPress) {
                option = (option + 1) % 4;
                showMenu(option);
            }

            // select option
            if (gPress) {
                runLesson(option);
                showMenu();
            }

            //  exit to main menu
            if (r && g) return;

            Thread.sleep(150);
        }
    }

    static void showMenu(int option) {
        System.out.println("\n--- SIMPLE MENU ---");
        System.out.println("0 Blink LED");
        System.out.println("1 Read Button");
        System.out.println("2 Buttons + LEDs");
        System.out.println("3 Tug of War");
        System.out.println("Selected: " + option);
    }

    static void showMenu() {
        showMenu(0);
    }

    static void runLesson(int o) throws Exception {

        System.out.println("\nRunning lesson " + o + "...\n");

        switch (o) {

            // Lesson 1
            case 0 -> {
                for (int i = 0; i < 5; i++) {
                    PhidgetManager.greenLED.setState(true);
                    Thread.sleep(300);
                    PhidgetManager.greenLED.setState(false);
                    Thread.sleep(300);
                }
            }

            // Lesson 2
            case 1 -> {
                for (int i = 0; i < 30; i++) {
                    System.out.println(
                        "RED: " + PhidgetManager.redButton.getState() +
                        " | GREEN: " + PhidgetManager.greenButton.getState()
                    );
                    Thread.sleep(200);
                }
            }

            // Lesson 3
            case 2 -> {
                for (int i = 0; i < 50; i++) {

                    PhidgetManager.redLED.setState(PhidgetManager.redButton.getState());
                    PhidgetManager.greenLED.setState(PhidgetManager.greenButton.getState());

                    Thread.sleep(100);
                }
            }

            // Prac
            case 3 -> tugOfWar();
        }
    }

    // Tug of War

    public static void tugOfWar() throws Exception {

        int red = 0;
        int green = 0;

        boolean lastR = false;
        boolean lastG = false;

        while (red < 10 && green < 10) {

            boolean r = PhidgetManager.redButton.getState();
            boolean g = PhidgetManager.greenButton.getState();

            if (r && !lastR) red++;
            if (g && !lastG) green++;

            lastR = r;
            lastG = g;

            System.out.println("RED: " + red + " GREEN: " + green);

            Thread.sleep(50);
        }

        boolean redWins = red >= 10;

        System.out.println(redWins ? "RED WINS" : "GREEN WINS");
        //both flash
        PhidgetManager.redLED.setState(true);
        PhidgetManager.greenLED.setState(true);
        Thread.sleep(400);

        PhidgetManager.redLED.setState(false);
        PhidgetManager.greenLED.setState(false);
        Thread.sleep(400);

        //WINNER FLASH 5 TIMES
        for (int i = 0; i < 5; i++) {

            if (redWins) {
                PhidgetManager.redLED.setState(true);
            } else {
                PhidgetManager.greenLED.setState(true);
            }

            Thread.sleep(250);

            PhidgetManager.redLED.setState(false);
            PhidgetManager.greenLED.setState(false);

            Thread.sleep(250);
        }
    }
}