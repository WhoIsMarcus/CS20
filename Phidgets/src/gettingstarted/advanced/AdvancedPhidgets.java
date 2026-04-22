package gettingstarted.advanced;

import gettingstarted.PhidgetManager;
import java.util.Arrays;

public class AdvancedPhidgets {

    static double[] highScores = {999, 999, 999};

    public static void run() throws Exception {

        reactionTimer();
        leaderboard();
    }

    static void reactionTimer() throws Exception {

        int countdown = 3;
        boolean started = false;
        boolean gameOver = false;
        long startTime = 0;

        while (!gameOver) {

            System.out.println("Countdown: " + countdown);

            // 🔥 GO SIGNAL
            if (countdown == 0 && !started) {

                System.out.println("GO!");

                PhidgetManager.greenLED.setState(true);
                Thread.sleep(300);
                PhidgetManager.greenLED.setState(false);

                startTime = System.currentTimeMillis();
                started = true;
            }

            // early press = fail
            if (!started && PhidgetManager.redButton.getState()) {
                System.out.println("TOO EARLY!");
                return;
            }

            // reaction press
            if (started && PhidgetManager.redButton.getState()) {

                double time = (System.currentTimeMillis() - startTime) / 1000.0;

                System.out.println("Reaction Time: " + time);

                addScore(time);

                gameOver = true;
            }

            countdown--;
            Thread.sleep(1000);
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

        for (int i = 0; i < highScores.length; i++) {
            if (t < highScores[i]) {
                highScores[i] = t;
                break;
            }
        }
    }
}