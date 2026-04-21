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


/*
Screen Dump: 

Enter number of bowlers: 2
Enter name for bowler 1: Marcus
Enter name for bowler 2: Abdalla

=== FRAME 1 ===
Marcus - Frame 1: Spare! (8 + 2)
Score this frame: 15
Total score: 15
---------------------------
Abdalla - Frame 1: Strike! (10)
Score this frame: 20
Total score: 20
---------------------------

=== FRAME 2 ===
Marcus - Frame 2: 2 + 1
Score this frame: 3
Total score: 18
---------------------------
Abdalla - Frame 2: 5 + 4
Score this frame: 9
Total score: 29
---------------------------

=== FRAME 3 ===
Marcus - Frame 3: Spare! (7 + 3)
Score this frame: 15
Total score: 33
---------------------------
Abdalla - Frame 3: 7 + 0
Score this frame: 7
Total score: 36
---------------------------

=== FRAME 4 ===
Marcus - Frame 4: 9 + 0
Score this frame: 9
Total score: 42
---------------------------
Abdalla - Frame 4: Spare! (9 + 1)
Score this frame: 15
Total score: 51
---------------------------

=== FRAME 5 ===
Marcus - Frame 5: 0 + 3
Score this frame: 3
Total score: 45
---------------------------
Abdalla - Frame 5: 9 + 0
Score this frame: 9
Total score: 60
---------------------------

=== FRAME 6 ===
Marcus - Frame 6: 3 + 0
Score this frame: 3
Total score: 48
---------------------------
Abdalla - Frame 6: 8 + 1
Score this frame: 9
Total score: 69
---------------------------

=== FRAME 7 ===
Marcus - Frame 7: 2 + 4
Score this frame: 6
Total score: 54
---------------------------
Abdalla - Frame 7: Spare! (2 + 8)
Score this frame: 15
Total score: 84
---------------------------

=== FRAME 8 ===
Marcus - Frame 8: 7 + 2
Score this frame: 9
Total score: 63
---------------------------
Abdalla - Frame 8: 2 + 7
Score this frame: 9
Total score: 93
---------------------------

=== FRAME 9 ===
Marcus - Frame 9: Strike! (10)
Score this frame: 20
Total score: 83
---------------------------
Abdalla - Frame 9: 1 + 7
Score this frame: 8
Total score: 101
---------------------------

=== FRAME 10 ===
Marcus - Frame 10: 7 + 1
Score this frame: 8
Total score: 91
---------------------------
Abdalla - Frame 10: 8 + 1
Score this frame: 9
Total score: 110
---------------------------

=== FINAL SCORES ===
Marcus: 91
Abdalla: 110


*/
