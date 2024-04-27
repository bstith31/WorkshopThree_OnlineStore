package com.ps;

import java.io.*;
import java.util.*;


// Class represents a product
class Product {
    private String sku;
    private String productName;
    private double price;
    private String department;

    public Product(String sku, String productName, double price, String department) {
        this.sku = sku;
        this.productName = productName;
        this.price = price;
        this.department = department;
    }

    public static void loadProducts(String file, ArrayList<Product> productsList) {
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Product{" +
                "sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", department='" + department + '\'' +
                '}';
    }

    // This class represents the store
    static class Store {
        Product[] products; // An array to store the products
        int productCount; // # of products in store.

        // Constructor
        public Store(ArrayList<Product> productsList) {
            products = new Product[10]; // Initial capacity of products array
            productCount = 0; // Make size 0.
        }

        // This is a method to load a product from a txt file.
        public static void loadProducts(String fileName, ArrayList<Product> productsList) {
            try (BufferedReader buffReader = new BufferedReader(new FileReader(fileName))) {
                String line;
                buffReader.readLine();
                while ((line = buffReader.readLine()) != null) {
                    String[] array = line.split("\\|");
                    String sku = array[0];
                    String productName = array[1];
                    double price = Double.parseDouble(array[2]);
                    String department = array[3];

                    Product product = new Product(sku, productName, price, department);
                    productsList.add(product);
                }
            } catch (IOException e) {
                System.out.println("Error reading product");
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
            Product[] result = new Product[productCount];
            int counter = 0; // This is a counter of a number of matching products
            // This next for loop will iterate through all the products in the store.
            for (int i = 0; i < productCount; i++) {
                Product product = products[i];
                // This next if statement checks to see if a keyword will match the product name or department
                if (product.productName.toLowerCase().contains(keyWord.toLowerCase()) || product.department.toLowerCase().contains(keyWord.toLowerCase())) {
                    result[counter++] = product; // This adds a matching product to the results array
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
                trimmedResults[i] = result[i];
            }
            return trimmedResults; // This returns the array of matching products
        }
    }

    // This is a class that represents the shopping cart
    public static class Cart {
        private Product[] items; // Array of cart items
        private int itemCount; // # of items in the cart

        // Constructor
        public Cart() {
            items = new Product[10]; // Initial capacity of the items array
            itemCount = 0; // Initialize item count
        }

        // Method to add a product to the cart
        public void addItem(Product product) {
            if (itemCount == items.length) {
                resize(); // Resize the array if it's needed
            }
            items[itemCount++] = product; // Add the product to the cart
        }

        // Method to remove a product from the cart
        public void removeItem(Product product) {
            int index = findProductIndex(product); // Find the index of the product
            if (index != -1) {
                // Shift elements to remove the product
                for (int i = index; i < itemCount - 1; i++) {
                    items[i] = items[i + 1];
                }
                items[--itemCount] = null; // Removes a product from the cart
            }
        }

        // Method to resize the items array
        private void resize() {
            Product[] newItems = new Product[items.length * 2]; // Double the size of the array
            // Copy elements from the old array to the new array
            System.arraycopy(items, 0, newItems, 0, items.length);
            items = newItems; // Update the reference to the new array
        }

        // Method to find the index of a product in the cart
        private int findProductIndex(Product product) {
            for (int i = 0; i < itemCount; i++) {
                if (items[i].getSku().equals(product.getSku())) {
                    return i; // Return the index if the product is found
                }
            }
            return -1; // Return -1 if the product is not found
        }

        // Method to calculate the total price of items in the cart
        public double calculateTotal() {
            double total = 0.0;
            for (int i = 0; i < itemCount; i++) {
                total += items[i].getPrice();
            }
            return total;
        }
    }
}


