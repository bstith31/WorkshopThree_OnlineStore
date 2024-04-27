package com.ps;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("WE FIXED IT!!!!!!!");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> productsList = new ArrayList<>();

        Product.loadProducts("Product.txt", productsList);

        // Create a new store
        Product.Store store = new Product.Store(productsList);

        // Display all products in the store
        store.displayProducts();

        // Search for products
        String keyWord = "searchTerm"; // Replace with your search term
        Product[] searchResults = store.searchProducts(keyWord);
        if (searchResults != null) {
            System.out.println("Search results:");
            for (Product product : searchResults) {
                System.out.println(product);
            }
        } else {
            System.out.println("No matching products found.");
        }

        // Create a new cart
        Product.Cart cart = new Product.Cart();

        // Add products to the cart
        Product product1 = new Product("sku1", "Product 1", 10.99, "Department 1");
        Product product2 = new Product("sku2", "Product 2", 9.99, "Department 2");
        cart.addItem(product1);
        cart.addItem(product2);

        // Remove a product from the cart
        cart.removeItem(product1);

        // Calculate the total price of items in the cart
        double total = cart.calculateTotal();
        System.out.println("Total price: $" + total);
    }
}
