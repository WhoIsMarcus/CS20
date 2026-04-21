/*

Program: ????.java          Last Date of this Revision: March 31, 2026

Purpose: 

Author: Marcus McCrea, 
School: CHHS
Course: Computer Programming 20

*/
package Mastery;

import java.util.ArrayList;
import java.util.Scanner;

// Item class
class Item {
    private int stockNumber;
    private String name;
    private int quantity;

    public Item(int stockNumber, String name, int quantity) {
        this.stockNumber = stockNumber;
        this.name = name;
        this.quantity = quantity;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void discontinue() {
        this.name = "discontinued";
        this.quantity = 0;
    }

    public void display() {
        System.out.println("Stock #: " + stockNumber +
                           " | Name: " + name +
                           " | Quantity: " + quantity);
    }
}

// Main application class
public class InventoryApp {
    private static ArrayList<Item> inventory = new ArrayList<>();
    private static int nextStockNumber = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Inventory Menu ---");
            System.out.println("1. Add Item");
            System.out.println("2. Discontinue Item");
            System.out.println("3. Display Item Stock");
            System.out.println("4. Display All Items");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addItem(scanner);
                    break;
                case 2:
                    discontinueItem(scanner);
                    break;
                case 3:
                    displayItem(scanner);
                    break;
                case 4:
                    displayAllItems();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        scanner.close();
    }

    // Add item
    private static void addItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        Item item = new Item(nextStockNumber, name, quantity);
        inventory.add(item);

        System.out.println("Item added with Stock #: " + nextStockNumber);
        nextStockNumber++;
    }

    // Discontinue item
    private static void discontinueItem(Scanner scanner) {
        System.out.print("Enter stock number to discontinue: ");
        int stockNumber = scanner.nextInt();

        for (Item item : inventory) {
            if (item.getStockNumber() == stockNumber) {
                item.discontinue();
                System.out.println("Item discontinued.");
                return;
            }
        }

        System.out.println("Item not found.");
    }

    // Display specific item
    private static void displayItem(Scanner scanner) {
        System.out.print("Enter stock number: ");
        int stockNumber = scanner.nextInt();

        for (Item item : inventory) {
            if (item.getStockNumber() == stockNumber) {
                item.display();
                return;
            }
        }

        System.out.println("Item not found.");
    }

    // Display all items
    private static void displayAllItems() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        for (Item item : inventory) {
            item.display();
        }
    }
}



/*
Screen Dump: 



--- Inventory Menu ---
1. Add Item
2. Discontinue Item
3. Display Item Stock
4. Display All Items
0. Exit
Enter choice: 4
Inventory is empty.

--- Inventory Menu ---
1. Add Item
2. Discontinue Item
3. Display Item Stock
4. Display All Items
0. Exit
Enter choice: 1
Enter item name: Bread
Enter quantity: 5
Item added with Stock #: 1000

--- Inventory Menu ---
1. Add Item
2. Discontinue Item
3. Display Item Stock
4. Display All Items
0. Exit
Enter choice: 1
Enter item name: Soda
Enter quantity: 20
Item added with Stock #: 1001

--- Inventory Menu ---
1. Add Item
2. Discontinue Item
3. Display Item Stock
4. Display All Items
0. Exit
Enter choice: 4
Stock #: 1000 | Name: Bread | Quantity: 5
Stock #: 1001 | Name: Soda | Quantity: 20

--- Inventory Menu ---
1. Add Item
2. Discontinue Item
3. Display Item Stock
4. Display All Items
0. Exit
Enter choice: 

*/