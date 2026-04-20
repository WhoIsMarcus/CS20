/*

Program: GradeBook.java          Last Date of this Revision: April 20, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.Scanner;


public class GradeBook {
    private int[][] grades;
    private final int STUDENTS = 12;
    private final int TESTS = 5;

    public GradeBook() {
        grades = new int[STUDENTS][TESTS];
    }

    public void getGrades() {
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < STUDENTS; i++) {
            System.out.println("Enter grades for Student " + (i + 1) + ":");
            for (int j = 0; j < TESTS; j++) {
                System.out.print("Test " + (j + 1) + ": ");
                grades[i][j] = input.nextInt();
            }
        }
    }

    public void showGrades() {
        System.out.println("\n--- Grade Book ---");

        for (int i = 0; i < STUDENTS; i++) {
            System.out.print("Student " + (i + 1) + ": ");
            for (int j = 0; j < TESTS; j++) {
                System.out.print(grades[i][j] + " ");
            }
            System.out.println();
        }
    }

    public double studentAvg(int student) {
        int sum = 0;
        for (int j = 0; j < TESTS; j++) {
            sum += grades[student][j];
        }
        return (double) sum / TESTS;
    }

    public double testAvg(int test) {
        int sum = 0;
        for (int i = 0; i < STUDENTS; i++) {
            sum += grades[i][test];
        }
        return (double) sum / STUDENTS;
    }
}

