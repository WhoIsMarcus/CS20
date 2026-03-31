/*

Program: ????.java          Last Date of this Revision: September 30, 2019

Purpose: 

Author: Your Name, 
School: CHHS
Course: Computer Programming ??
 

*/
package Mastery;

import java.util.Scanner;

public class RootSolver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for coefficients
        System.out.print("Enter coefficient a: ");
        double a = scanner.nextDouble();

        System.out.print("Enter coefficient b: ");
        double b = scanner.nextDouble();

        System.out.print("Enter coefficient c: ");
        double c = scanner.nextDouble();

        // Ensure a is not zero
        if (a == 0) {
            System.out.println("This is not a quadratic equation (a cannot be 0).");
            return;
        }

        // Compute the discriminant
        double discriminant = b * b - 4 * a * c;

        System.out.println("\nQuadratic equation: " + a + "x^2 + " + b + "x + " + c + " = 0");

        // Only consider real roots
        if (discriminant > 0) {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            System.out.println("The equation has two real roots: " + root1 + " and " + root2);
        } else if (discriminant == 0) {
            double root = -b / (2 * a);
            System.out.println("The equation has one real root: " + root);
        } else {
            System.out.println("The equation has no real roots.");
        }

        scanner.close();
    }
}

/*
Screen Dump: 
Enter coefficient a: 3
Enter coefficient b: 4
Enter coefficient c: 5

Quadratic equation: 3.0x^2 + 4.0x + 5.0 = 0
The equation has no real roots.

Enter coefficient a: 2
Enter coefficient b: 1
Enter coefficient c: -6

Quadratic equation: 2.0x^2 + 1.0x + -6.0 = 0
The equation has two real roots: 1.5 and -2.0

*/
