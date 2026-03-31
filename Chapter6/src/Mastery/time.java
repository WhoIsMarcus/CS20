/*

Program: ????.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.Scanner;


public class time {

	
	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);  //init scanner
		System.out.println("Enter The Starting Hour: ");
		int hour = scanner.nextInt(); //get the starting hour
		System.out.println("Enter AM or PM: ");
		
		String f = scanner.next();  //filler
		if(f.equalsIgnoreCase("pm") ){ //if the time is pm we add 12 hours to it (converting to 24 hour clock)
			hour += 12;
		}
		System.out.println("Enter The Number Of Elapsed Hours: ");
		int elapsed = scanner.nextInt(); 
		int final24 = (hour + elapsed) % 24;  //add the number of elapsed hours and mod 24
		if (final24>12) {
			System.out.println("The time is: "+(final24%12) + "PM");  //if mod 24 is greater than 12 it means its PM
			
		}else {
			System.out.println("The time is: "+(final24) + "AM"); 
		}
		
		
		
		
		
		
	}

}

/*
Screen Dump: 
Enter The Starting Hour: 
6
Enter AM or PM: 
am
Enter The Number Of Elapsed Hours: 
34
The time is: 4PM

Enter The Starting Hour: 
1
Enter AM or PM: 
pm
Enter The Number Of Elapsed Hours: 
2
The time is: 3PM


*/



