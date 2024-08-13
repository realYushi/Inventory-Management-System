package me.yushi.inventorymanagementsystem.InventoryManagementSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Inventory Management System");
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Add Product");
            System.out.println("2. View Inventory");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.println("Add Product selected");
                    // TODO: Implement add product functionality
                    break;
                case 2:
                    System.out.println("View Inventory selected");
                    // TODO: Implement view inventory functionality
                    break;
                case 3:
                    System.out.println("Update Product selected");
                    // TODO: Implement update product functionality
                    break;
                case 4:
                    System.out.println("Delete Product selected");
                    // TODO: Implement delete product functionality
                    break;
                case 5:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        scanner.close();
        System.out.println("Thank you for using the Inventory Management System");
    }
}
