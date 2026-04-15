/*

Program: MathTutor.java          Last Date of this Revision: March 16, 2026
Purpose: 

Author: Marcus McCrea, 
School: CHHS,
Course: Computer Programming 20
 

*/
package Mastery;

import java.util.Random;
import java.util.Scanner;

public class MathTutor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        // Generate two random numbers between 1 and 10
        int num1 = rand.nextInt(10) + 1; // 1–10
        int num2 = rand.nextInt(10) + 1; // 1–10

        // Randomly pick an operator: +, -, *, /
        char[] operators = {'+', '-', '*', '/'};
        char operator = operators[rand.nextInt(4)];

        double correctAnswer = 0;

        // Calculate the correct answer based on operator
        switch (operator) {
            case '+':
                correctAnswer = num1 + num2;
                break;
            case '-':
                correctAnswer = num1 - num2;
                break;
            case '*':
                correctAnswer = num1 * num2;
                break;
            case '/':
                // Avoid dividing by zero (though numbers are 1-10)
                correctAnswer = (double) num1 / num2;
                break;
        }

        // Prompt the user
        System.out.printf("What is " + num1 + " "+operator+" "+ num2 + ": ");
        double userAnswer = scanner.nextDouble();

        // Check the answer (for division allow small error due to decimals)
        if (operator == '/' && Math.abs(userAnswer - correctAnswer) < 0.001) {
            System.out.println("Correct!");
        } else if (userAnswer == correctAnswer) {
            System.out.println("Correct!");
        } else {
            System.out.printf("Incorrect. The correct answer is %.2f%n", correctAnswer);
        }

        scanner.close(); //close scanner
    }
}

/*
Screen Dump: 
What is 2 / 8: 200
Incorrect. The correct answer is 0.25

What is 8 + 7: 15
Correct!

*/
