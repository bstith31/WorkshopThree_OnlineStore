package com.ps;

import java.io.*;
import java.util.*;

public class Product {

    // SKU|Product Name|Price|Department

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


    public void loadProducts(String fileName, ArrayList<Product> productsList) {
        try (BufferedReader buffReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            buffReader.readLine();
            while ((line = buffReader.readLine()) != null) {
                String [] array = line.split("\\|");
                String sku = array[0];
                String productName = array[1];
                double price = Double.parseDouble(array[2]);
                String department = array[3];

                Product product = new Product (sku, productName, price, department);
             // ProductsList.add
            }

        } catch (IOException e) {
            System.out.println("Error reading product");

        }
    }





}