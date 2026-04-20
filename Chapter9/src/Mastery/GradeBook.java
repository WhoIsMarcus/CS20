/*

Program: GradeBook.java          Last Date of this Revision: April 20, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.Scanner;

class GradeBook {
    private int[][] grades; // array for 12 students and 5 tests
    private final int STUDENTS = 12;
    private final int TESTS = 5;

    public GradeBook() {
        grades = new int[STUDENTS][TESTS];
    }

    //input grades
    public void getGrades() {
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < STUDENTS; i++) {
            System.out.println("Enter grades for the student " + (i + 1) + ":");
            for (int j = 0; j < TESTS; j++) {
                System.out.print("Test " + (j + 1) + ": ");
                grades[i][j] = input.nextInt();
            }
        }
    }

    // display all grades
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

    // Method to calculate student avg
    public double studentAvg(int student) {
        int sum = 0;

        for (int j = 0; j < TESTS; j++) {
            sum += grades[student][j];
        }

        return (double) sum / TESTS;
    }

    // Method to calculate test average
    public double testAvg(int test) {
        int sum = 0;

        for (int i = 0; i < STUDENTS; i++) {
            sum += grades[i][test];
        }

        return (double) sum / STUDENTS;
    }
}

	class CourseGrades {
    public static void main(String[] args) {
        GradeBook gb = new GradeBook();

        gb.getGrades();     // Input grades
        gb.showGrades();    // Display grades

        System.out.println("\n--- Student Avg ---");
        for (int i = 0; i < 12; i++) {
            System.out.println("Student " + (i + 1) + " Avg: " + gb.studentAvg(i));
        }

        System.out.println("\n--- Test Averages ---");
        for (int i = 0; i < 5; i++) {
            System.out.println("Test " + (i + 1) + " Avg: " + gb.testAvg(i));
        }
    }
}

/*
Screen Dump: 

Enter grades for the student 1:
Test 1: 12
Test 2: 15
Test 3: 84
Test 4: 100
Test 5: 100
Enter grades for the student 2:
Test 1: 100
Test 2: 100
Test 3: 100
Test 4: 100
Test 5: 1
Enter grades for the student 3:
Test 1: 100
Test 2: 56
Test 3: 57
Test 4: 58
Test 5: 59
Enter grades for the student 4:
Test 1: 6
Test 2: 61
Test 3: 100
Test 4: 100
Test 5: 100
Enter grades for the student 5:
Test 1: 89
Test 2: 90
Test 3: 91
Test 4: 92
Test 5: 93
Enter grades for the student 6:
Test 1: 94
Test 2: 95
Test 3: 96
Test 4: 97
Test 5: 98
Enter grades for the student 7:
Test 1: 99
Test 2: 100
Test 3: 50
Test 4: 51
Test 5: 52
Enter grades for the student 8:
Test 1: 53
Test 2: 54
Test 3: 65
Test 4: 56
Test 5: 57
Enter grades for the student 9:
Test 1: 58
Test 2: 59
Test 3: 60
Test 4: 61
Test 5: 62
Enter grades for the student 10:
Test 1: 63
Test 2: 64
Test 3: 65
Test 4: 66
Test 5: 67
Enter grades for the student 11:
Test 1: 68
Test 2: 69
Test 3: 70
Test 4: 71
Test 5: 72
Enter grades for the student 12:
Test 1: 73
Test 2: 74
Test 3: 75
Test 4: 76
Test 5: 77

--- Grade Book ---
Student 1: 12 15 84 100 100 
Student 2: 100 100 100 100 1 
Student 3: 100 56 57 58 59 
Student 4: 6 61 100 100 100 
Student 5: 89 90 91 92 93 
Student 6: 94 95 96 97 98 
Student 7: 99 100 50 51 52 
Student 8: 53 54 65 56 57 
Student 9: 58 59 60 61 62 
Student 10: 63 64 65 66 67 
Student 11: 68 69 70 71 72 
Student 12: 73 74 75 76 77 

--- Student Avg ---
Student 1 Avg: 62.2
Student 2 Avg: 80.2
Student 3 Avg: 66.0
Student 4 Avg: 73.4
Student 5 Avg: 91.0
Student 6 Avg: 96.0
Student 7 Avg: 70.4
Student 8 Avg: 57.0
Student 9 Avg: 60.0
Student 10 Avg: 65.0
Student 11 Avg: 70.0
Student 12 Avg: 75.0

--- Test Averages ---
Test 1 Avg: 67.91666666666667
Test 2 Avg: 69.75
Test 3 Avg: 76.08333333333333
Test 4 Avg: 77.33333333333333
Test 5 Avg: 69.83333333333333

*/

