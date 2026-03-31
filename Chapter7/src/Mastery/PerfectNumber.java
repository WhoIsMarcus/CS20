



/*

Program: PerfectNumber.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.Scanner;


public class PerfectNumber{

    // Method to check if a number is perfect
    public static boolean isPerfect(int number) {
        int sum = 0;

        // Sum all divisors of the number except itself
        for (int i = 1; i <= number / 2; i++) {
            if (number % i == 0) {
                sum += i;
            }
        }

        // A number is perfect if the sum of its proper divisors equals the number
        return sum == number;
    }

    public static void main(String[] args) {
    	
    	Scanner scanner = new Scanner(System.in);  //init scanner
    	
    	System.out.println("Enter the upper limit you would like to check for perfect intgers in: ");
    	int num = scanner.nextInt();
    	
    	
        System.out.println("Perfect numbers up to "+num+":");

        // Check all numbers from 1 to Whatever the user picks
        for (int i = 1; i <= num; i++) {
            if (isPerfect(i)) {
                System.out.println(i);
            }
        }
    }
}


/*
Screen Dump: 
Enter the upper limit you would like to check for perfect intgers in: 
100
Perfect numbers up to 100:
6
28


Enter the upper limit you would like to check for perfect intgers in: 
1000
Perfect numbers up to 1000:
6
28
496



*/

