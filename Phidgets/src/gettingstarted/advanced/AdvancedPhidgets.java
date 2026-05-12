/*

Program: AdvancedPhidgets.java          Last Date of this Revision: March 31, 2026

Purpose: this is all of the advanced phidgets projects in one simple file so you can choose which one to try

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/


package gettingstarted.advanced;

import gettingstarted.PhidgetManager;
import java.util.Arrays;
import java.util.Random;

public class AdvancedPhidgets {

    static double[] highScores = {999, 999, 999};

    public static void run() throws Exception {
        reactionTimer();
        leaderboard();
    }

    static void reactionTimer() throws Exception {
        // Use a random delay so the player can't predict the "GO" signal
        int countdown = 2000 + new Random().nextInt(3000); 
        boolean started = false;
        long startTime = 0;

        System.out.println("Wait for it...");

        while (true) {
            // 1. Check for Early Press
            if (!started && PhidgetManager.redButton.getState()) {
                System.out.println("TOO EARLY! Disqualified.");
                return;
            }

            // 2. Handle the "GO" Signal
            if (!started && countdown <= 0) {
                System.out.println("GO!");
                PhidgetManager.greenLED.setState(true);
                startTime = System.currentTimeMillis();
                started = true;
            }

            // 3. Handle the Reaction Press
            if (started && PhidgetManager.redButton.getState()) {
                double time = (System.currentTimeMillis() - startTime) / 1000.0;
                PhidgetManager.greenLED.setState(false); // Turn off LED on press
                System.out.println("Reaction Time: " + time + "s");
                addScore(time);
                break; // Game Over
            }

            // 4. THE FIX: Small sleep (1ms) keeps the loop "tight" and responsive
            Thread.sleep(1);
            if (!started) {
                countdown--;
            }
        }
    }

    static void leaderboard() {
        Arrays.sort(highScores);
        System.out.println("\n--- TOP TIMES ---");
        for (double t : highScores) {
            if (t != 999) System.out.println(t + "s");
        }
    }

    static void addScore(double t) {
        // Find the worst score (the highest one) and replace it if new time is better
        Arrays.sort(highScores); 
        if (t < highScores[2]) {
            highScores[2] = t;
        }
    }
}
