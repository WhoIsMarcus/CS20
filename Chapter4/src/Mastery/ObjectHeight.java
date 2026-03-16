// Exercise 1

package Mastery;

import java.util.Scanner;


public class ObjectHeight {

	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in); //Initiate Scanner
		System.out.println("Enter A Time Less than 4.5 seconds: "); 
		double t = scanner.nextDouble(); //Checks for the next double in the console and assigns it to t
		double height = 100 - 4.9*Math.pow(t, 2); //calculates the height using the given formula
		System.out.println("The Height Of The Object Is: "+ height + " Meters");
		
		scanner.close(); //close scanner

	}

}

/*
Screen Dump:

Enter A Time Less than 4.5 seconds: 
3
The Height Of The Object Is: 55.9 Meters
*/