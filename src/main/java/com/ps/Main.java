package com.ps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Product {
    public String sku;
    public String productName;
    public double price;
    public String department;

    public Product(String sku, String productName, double price, String department) {
        this.sku = sku;
        this.productName = productName;
        this.price = price;
        this.department = department;
    }

    // This class represents the store
    static class Store {
        Product[] products; // An array to store the products
        int productCount; // # of products in store.

        // Constructor
        public Store() {
            products = new Product[10]; // Initial capacity of products array
            productCount = 0; // Make size 0.
        }

        // This is a method to load products from a file
        public void loadProducts(String filename) {
            try (BufferedReader buffReader = new BufferedReader(new FileReader(filename))) {
                String line;
                // Read each line from the file
                buffReader.readLine();
                while ((line = buffReader.readLine()) != null) {
                    String[] data = line.split("\\|"); // Split the line by pipe (|)
                    if (productCount == products.length) {
                        reSize(); // Resize array if needed
                    }
                    // Create a new Product object and add it to the products array
                    products[productCount++] = new Product(data[0], data[1], Double.parseDouble(data[2]), data[3]);
                }
            } catch (IOException e) {
                System.out.println("Error loading products: " + e.getMessage()); // Print error message if loading fails
            }
        }

        // This is a method to resize the products array
        public void reSize() {
            Product[] newProducts = new Product[products.length * 2]; // This doubles the size of the array
            // This copies elements from the old array to the new array.
            for (int i = 0; i < productCount; i++) {
                newProducts[i] = products[i];
            }
            products = newProducts; // This updates the reference to the new array
        }

        // This is a method to display all the products in the store.
        public void displayProducts() {
            for (int i = 0; i < productCount; i++) {
                Product product = products[i];
                // This prints the details of each product.
                System.out.printf("SKU: %s | Name: %s | Price: $%.2f | Department: %s%n", product.sku, product.productName, product.price, product.department);
            }
        }

        // This is a method to search for products based on a keyword
        public Product[] searchProducts(String keyWord) { // This is an array to store search results
            Product[] results = new Product[productCount];
            int counter = 0; // This is a counter of a number of matching products
            // This next for loop will iterate through all the products in the store.
            for (int i = 0; i < productCount; i++) {
                Product product = products[i];
                // This next if statement checks to see if a keyword will match the product name or department
                if (product.productName.toLowerCase().contains(keyWord.toLowerCase()) || product.department.toLowerCase().contains(keyWord.toLowerCase())) {
                    results[counter++] = product; // This adds a matching product to the results array
                }
            }
            // If no matching products are found, return null
            if (counter == 0) {
                return null;
            }
            // This creates a new array with a size equal to the number of a matching products.
            Product[] trimmedResults = new Product[counter];
            // This for loop copy matches products to the new array
            for (int i = 0; i < counter; i++) {
                trimmedResults[i] = results[i];
            }
            return trimmedResults; // This returns the array of matching products
        }
    }

    // This is a class that represents the shopping cart
    static class Cart {
        Product[] items; // Array of cart items
        int itemCount; // # of items in the cart

        // Constructor
        public Cart() {
            items = new Product[10]; // Initial capacity of the items array
            itemCount = 0; // Initialize item count
        }

        // Method to add a product to the cart
        public void addItem(Product product) {
            if (itemCount == items.length) {
                reSize(); // Resize the array if it's needed
            }
            items[itemCount++] = product; // Add the product to the cart
        }

        // Method to remove a product from the cart
        public void removeItem(Product product) {
            int index = -1; // Find the index of the product
            // Find an index of the product in this item array.
            for (int i = 0; i < itemCount; i++) {
                if (items[i].sku.equals(product.sku)) {
                    index = i;
                    break;
                }
            }
            // If product is found, removes it from the cart
            if (index != -1) {
                // This shifts the elements to the left to fill the gap.
                for (int i = index; i < itemCount - 1; i++) {
                    items[i] = items[i + 1];
                }
                itemCount--;
            }
        }

        // Method to resize the items array
        private void reSize() {
            Product[] newItems = new Product[items.length * 2]; // Double the size of the array
            // Copy elements from the old array to the new array
            for (int i = 0; i < itemCount; i++) {
                newItems[i] = items[i];
            }
            items = newItems; // Update the reference to the new array
        }

        // Method to calculate the total price of items in the cart
        public double calculateTotal() {
            double total = 0;
            for (int i = 0; i < itemCount; i++) {
                total += items[i].price;
            }
            return total;
        }
    }
}

// Main class to run the application
public class Main {
    public static void main(String[] args) {
        Product.Store store = new Product.Store(); // Create a new store object
        store.loadProducts("product.txt"); // Load products from CSV file
        Product.Cart cart = new Product.Cart(); // Create a new shopping cart object

        Scanner scanner = new Scanner(System.in); // Scanner object to read user input

        // Main loop to display menu and handle user actions
        while (true) {
            System.out.println("Store Home Screen:");
            System.out.println("1. Display Products");
            System.out.println("2. Display Cart");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Display Products
                    while (true) {
                        System.out.println("Display Products:");
                        System.out.print("Enter product name, price, or department to search (or press Enter to show all): ");
                        scanner.nextLine(); // Consume newline left by previous nextInt()
                        String searchTerm = scanner.nextLine().trim();
                        if (searchTerm.isEmpty()) {
                            store.displayProducts(); // Display all products if search term is empty
                        } else {
                            Product[] searchResults = store.searchProducts(searchTerm); // Search for products based on the keyword
                            if (searchResults != null && searchResults.length > 0) {
                                // Display the search results
                                for (Product product : searchResults) {
                                    System.out.printf("SKU: %s | Name: %s | Price: $%.2f | Department: %s%n", product.sku, product.productName, product.price, product.department);
                                }
                            } else {
                                System.out.println("No matching products found.");
                            }
                        }

                        // Prompt the user for action.
                        System.out.println("Options: (A)dd to cart, (B)ack to home");
                        System.out.print("Enter your choice: ");
                        String userAction = scanner.next().trim().toLowerCase();

                        if (userAction.equals("a")) { // Add to Cart
                            System.out.print("Enter SKU of the product you want to add to cart: ");
                            String sku = scanner.next().trim();
                            // Find the product with the given SKU in the store
                            Product productToAdd = null;
                            for (Product p : store.products) {
                                if (p.sku.equals(sku)) {
                                    productToAdd = p;
                                    break;
                                }
                            }
                            if (productToAdd != null) {
                                cart.addItem(productToAdd); // This adds the product to the cart.
                                System.out.println("Product added to cart.");
                            } else {
                                System.out.println("Invalid SKU.");
                            }
                        } else if (userAction.equals("b")) { // Back to Home
                            break;
                        } else {
                            System.out.println("Invalid option.");
                        }
                    }
                    break;

                case 2: // Display Cart
                    System.out.println("Display Cart:");
                    if (cart.itemCount > 0) { // This is if cart is not empty
                        // This displays cart items.
                        for (int i = 0; i < cart.itemCount; i++) {
                            Product item = cart.items[i];
                            System.out.printf("Name: %s | Price: $%.2f%n", item.productName, item.price);
                        }
                        // This displays the  total price.
                        System.out.printf("Total: $%.2f%n", cart.calculateTotal());

                        // Prompts user for any input/action
                        System.out.println("Options: (C)heck out, (R)emove from cart, (B)ack to home");
                        System.out.print("Enter your choice: ");
                        String action = scanner.next().trim().toLowerCase();

                        if (action.equals("c")) { // Checks out
                            System.out.println("Checking out...");
                            // Implement checkout logic here
                            break;
                        } else if (action.equals("r")) { // Remove from Cart
                            System.out.print("Enter SKU of the product you want to remove from cart: ");
                            String sku = scanner.next().trim();
                            // Find the product with the given SKU in the cart
                            Product productToRemove = null;
                            for (Product p : cart.items) {
                                if (p != null && p.sku.equals(sku)) {
                                    productToRemove = p;
                                    break;
                                }
                            }
                            if (productToRemove != null) {
                                cart.removeItem(productToRemove); // Remove product from cart
                                System.out.println("The product is removed from the cart.");
                            } else {
                                System.out.println("The product is not found in the cart.");
                            }
                        } else if (action.equals("b")) { // Back to Home
                            break;
                        } else {
                            System.out.println("Invalid, try again");
                        }
                    } else {
                        System.out.println("The cart is empty!");
                    }
                    break;

                case 3: // Exit
                    System.out.println("Exiting the program...");
                    System.exit(0); // Exit the program
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}