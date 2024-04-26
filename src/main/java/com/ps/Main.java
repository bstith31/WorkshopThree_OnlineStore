package com.ps;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("WE FIXED IT!!!!!!!");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> productsList = new ArrayList<>();

        Product product = new Product(null, null, 0.0, null);
        product.loadProducts("Product.txt", productsList);


        for (Product p : productsList) {
            System.out.println(p);
        }
    }
}
