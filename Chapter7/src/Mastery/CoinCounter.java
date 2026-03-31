/*

Program: CoinCounter.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.Scanner;


public class CoinCounter {
	
	public static double Counter(double p, double n, double d, double q) {
		
		p = p * 0.01;
		n = n * 0.05;
		d = d * 0.10;
		q = q * 0.25;
		
		double total = p + n + d + q;
		return total;
		
	}
	
	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);  //init scanner
		System.out.println("Enter Your Coin Amounts: ");
		System.out.print("Quaters: ");
		double q = scanner.nextDouble();
		System.out.print("Dimes: ");
		double d = scanner.nextDouble();
		System.out.print("Nickels: ");
		double n = scanner.nextDouble();
		System.out.print("Pennies: ");
		double p = scanner.nextDouble();
		
		double end = Counter(p, n, d, q);
		
		System.out.println("Total: $" + end);
		
		
		

	}

}

/*
Screen Dump: 

Enter Your Coin Amounts: 
Quaters: 3
Dimes: 4
Nickels: 1
Pennies: 2
Total: $1.22


Enter Your Coin Amounts: 
Quaters: 10000
Dimes: 28
Nickels: 12
Pennies: 1
Total: $2503.41


*/

