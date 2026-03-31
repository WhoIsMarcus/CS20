/*

Program: ????.java          Last Date of this Revision: September 30, 2019

Purpose: 

Author: Your Name, 
School: CHHS
Course: Computer Programming ??
 

*/
package Mastery;

import java.util.Scanner;


public class PizzaCost {

	//Used for finding the cost of Materials
	public static double mats(double d)
	{
		return 0.05 * Math.pow(d, 2);
	}
	
	public static void main(String[] args) 
	{
		//Set Vars
		double Labor = 0.75;
		double Rent = 1.00; 
		
		Scanner scanner = new Scanner(System.in); //Initiate Scanner
		System.out.println("Enter The Diameter (In) Of Your Pizza: ");
		double d = scanner.nextDouble();
		
		double matcost = mats(d); //use the previously defined func to find the total cost of mats
		
		double finalcost = matcost + Rent + Labor; //add all the costs 
		
		System.out.println("The Cost Of Making The Pizza Is: "+ finalcost);
		
		
		scanner.close(); //close scanner

	}

}

/*
Screen Dump
Enter The Diameter (In) Of Your Pizza: 
12
The Cost Of Making The Pizza Is: 8.95

*/
