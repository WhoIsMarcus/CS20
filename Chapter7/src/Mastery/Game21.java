/*

Program: ????.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.Scanner;

public class Game21 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String playAgain = "yes";

        while (playAgain.equalsIgnoreCase("yes")) {

            Player player = new Player();
            Player computer = new Player();

            // Initial 2 cards
            player.addCard(new Card());
            player.addCard(new Card());

            computer.addCard(new Card());
            computer.addCard(new Card());

            System.out.println("\n--- Your Turn ---");
            player.showHand();

            // Player decision
            System.out.print("Do you want a third card? (yes/no): ");
            String choice = input.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                player.addCard(new Card());
                player.showHand();
            }

            System.out.println("\n--- Computer ---");
            computer.showHand();

            int playerScore = player.getScore();
            int compScore = computer.getScore();

            // Determine winner
            System.out.println("\n--- Result ---");

            if (playerScore > 21) {
                System.out.println("You busted! Computer wins.");
            } else if (compScore > 21) {
                System.out.println("Computer busted! You win.");
            } else if (playerScore > compScore) {
                System.out.println("You win!");
            } else if (compScore > playerScore) {
                System.out.println("Computer wins.");
            } else {
                System.out.println("It's a tie!");
            }

            // Play again?
            System.out.print("\nPlay again? (yes/no): ");
            playAgain = input.nextLine();
        }

        input.close();
    }
}


/*
Screen Dump: 
--- Your Turn ---
Cards: 5 9  | Score: 14
Do you want a third card? (yes/no): yes
Cards: 5 9 7  | Score: 21

--- Computer ---
Cards: 6 10  | Score: 16

--- Result ---
You win!

Play again? (yes/no): no




--- Your Turn ---
Cards: 3 10  | Score: 13
Do you want a third card? (yes/no): yes
Cards: 3 10 11  | Score: 14

--- Computer ---
Cards: 10 8  | Score: 18

--- Result ---
Computer wins.

Play again? (yes/no): no

*/

		
		
		