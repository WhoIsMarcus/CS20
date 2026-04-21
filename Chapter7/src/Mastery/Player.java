/*

Program: ????.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getScore() {
        int total = 0;
        int aceCount = 0;

        for (Card c : hand) {
            total += c.getValue();
            if (c.getValue() == 11) {
                aceCount++;
            }
        }

        // Adjust Ace from 11 → 1 if bust
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    public void showHand() {
        System.out.print("Cards: ");
        for (Card c : hand) {
            System.out.print(c.getValue() + " ");
        }
        System.out.println(" | Score: " + getScore());
    }
}