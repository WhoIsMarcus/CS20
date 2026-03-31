





/*

Program: ????.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.Scanner;


public class digitsum {
	
	
	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);  //init scanner
		System.out.println("Enter Your Number: ");
		int number = scanner.nextInt();
		
		int sum = 0;
		int tempNumber = number; // Use a temporary variable to avoid modifying the original number

		while (tempNumber != 0) {
		    int digit = tempNumber % 10; // Extract the last digit (e.g., 5 from 12345)
		    sum = sum + digit;         // Add the digit to the sum
		    tempNumber = tempNumber / 10; // Remove the last digit (e.g., 12345 becomes 1234)
		}

		System.out.println("The sum of the digits of " + number + " is: " + sum);
		
		

	}

}

/*
Screen Dump: 

Enter Your Number: 
147993993
The sum of the digits of 147993993 is: 54




*/

