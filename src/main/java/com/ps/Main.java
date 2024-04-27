package com.ps;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> productsList = new ArrayList<>();
        Product.Store store = new Product.Store(productsList);
        Product.Cart cart = new Product.Cart();

        Product.Store.loadProducts("Product.txt", productsList, store);

        boolean exit = false;

        while (!exit) {
            System.out.println("=== Store Home Screen ===");
            System.out.println("1. Display Products");
            System.out.println("2. Search Products");
            System.out.println("3. Display Cart");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    store.displayProducts();
                    break;
                case 2:
                    System.out.print("Enter search term: ");
                    String searchTerm = scanner.nextLine();
                    searchAndDisplayResults(store, searchTerm);
                    break;
                case 3:
                    displayCart(cart);
                    break;
                case 4:
                    System.out.println("Exiting the application...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Method to display search results
    private static void searchAndDisplayResults(Product.Store store, String searchTerm) {
        Product[] searchResults = store.searchProducts(searchTerm);
        if (searchResults != null) {
            System.out.println("Search results:");
            for (Product product : searchResults) {
                System.out.println(product);
            }
        } else {
            System.out.println("No matching products found.");
        }
    }

    // Method to display the items in the cart
    private static void displayCart(Product.Cart cart) {
        double total = cart.calculateTotal();
        System.out.println("Total price: $" + total);
    }
}