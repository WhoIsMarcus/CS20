/*

Program: Frame.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;



import java.util.Random;

public class Frame {
    private int throw1;
    private int throw2;
    private boolean strike;
    private boolean spare;

    public Frame() {
        Random rand = new Random();

        // First throw
        throw1 = rand.nextInt(11); // 0–10

        if (throw1 == 10) {
            strike = true;
            throw2 = 0;
        } else {
            throw2 = rand.nextInt(11 - throw1); // remaining pins
            if (throw1 + throw2 == 10) {
                spare = true;
            }
        }
    }

    public int getScore() {
        if (strike) return 20;
        if (spare) return 15;
        return throw1 + throw2;
    }

    public String toString() {
        if (strike) return "Strike! (10)";
        if (spare) return "Spare! (" + throw1 + " + " + throw2 + ")";
        return throw1 + " + " + throw2;
    }
}