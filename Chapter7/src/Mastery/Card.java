/*

Program: ????.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.Random;

public class Card {
    private int value;

    public Card() {
        Random rand = new Random();
        int num = rand.nextInt(13) + 1; // 1–13

        if (num >= 11) {
            value = 10; // Jack, Queen, King
        } else if (num == 1) {
            value = 11; // Ace starts as 11
        } else {
            value = num;
        }
    }

    public int getValue() {
        return value;
    }
}



