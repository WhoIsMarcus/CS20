/*

Program: ????.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;
import java.util.ArrayList;
import java.util.Scanner;

public class BowlingGame {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Bowler> bowlers = new ArrayList<>();

        System.out.print("Enter number of bowlers: ");
        int numBowlers = input.nextInt();
        input.nextLine();

        // Create bowlers
        for (int i = 0; i < numBowlers; i++) {
            System.out.print("Enter name for bowler " + (i + 1) + ": ");
            String name = input.nextLine();
            bowlers.add(new Bowler(name));
        }

        // 10 frames
        for (int frame = 1; frame <= 10; frame++) {
            System.out.println("\n=== FRAME " + frame + " ===");

            for (Bowler b : bowlers) {
                b.bowlFrame(frame);
            }
        }

        // Final Scores
        System.out.println("\n=== FINAL SCORES ===");
        for (Bowler b : bowlers) {
            System.out.println(b.getName() + ": " + b.getScore());
        }

        input.close();
    }
}