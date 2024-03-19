package org.murugappan.service;

import org.murugappan.DAO.ProductsDAO;
import org.murugappan.DAO.ProductsImpl;
import org.murugappan.model.ProductDetails;

import java.util.Scanner;

// Service Class For ProductsService
public class ProductsService {
    // Object Declaration
    ProductDetails details = new ProductDetails();
    ProductsDAO productsImplementation = new ProductsImpl();
    Scanner input = new Scanner(System.in); // Creating a single Scanner object for user input

    // Service Method To Add Products Into Cart
    public void addProduct() {
        System.out.println("Enter The Product Name");
        details.productDetails.put("ProductName", input.nextLine());
        System.out.println("Enter The Cost  Price");
        details.productDetails.put("CostPrice", input.nextLine());
        System.out.println("Enter The Selling Price");
        details.productDetails.put("SellingPrice", input.nextLine());
        System.out.println("Enter The StockNo");
        details.productDetails.put("Stock", input.nextLine());
        System.out.println("Enter The Product Description");
        details.productDetails.put("Description", input.nextLine());
        System.out.println("Enter The Tax Percent");
        details.productDetails.put("Tax", input.nextLine());
        productsImplementation.addProducts(details.productDetails.get("ProductName"), details.productDetails.get("Description"), Integer.parseInt(details.productDetails.get("CostPrice")), Integer.parseInt(details.productDetails.get("SellingPrice")), Integer.parseInt(details.productDetails.get("Stock")), Integer.parseInt(details.productDetails.get("Tax"))); // Method Call TO Add Products
    }

    // Service Method To Delete Products Into Cart
    public void deleteProduct() {
        System.out.println("Enter The ProductId To Be Deleted");
        int productId = input.nextInt();
        productsImplementation.deleteProduct(productId); // Method Call To Delete Products
    }

    // Service Method To show Products Into Cart
    public void showProducts() {
        System.out.println("Here The List of Available Products In the Inventory!");
        System.out.println();
        productsImplementation.showProducts(); // Method Call To Show Products
    }

    // Service Method To Edit Products Into Cart
    public void editProducts() {
        System.out.println("Enter the detail to be edited:\n" +
                "1 To  Edit Product Name\n" +
                "2 To Edit Product Selling Price\n" +
                "3 To Edit Cost Price\n" +
                "4 To Edit Product Description\n" +
                "5 To Edit The Total Stock\n" +
                "6 To Edit The Tax Percent");

        int choice = input.nextInt();
        String columnToUpdate = null;
        int productId;

        switch (choice) {
            case 1:
                columnToUpdate = "product_name";
                break;
            case 2:
                columnToUpdate = "selling_price";
                break;
            case 3:
                columnToUpdate = "cost_price";
                break;
            case 4:
                columnToUpdate = "product_des";
                break;
            case 5:
                columnToUpdate = "Stock";
                break;
            case 6:
                columnToUpdate = "Tax_Percent";
                break;
            default:
                System.out.println("Enter a valid choice");
                return;
        }

        System.out.println("Enter the product ID:");
        productId = input.nextInt();
        System.out.println("Enter the new value to be updated:");
        String newValue = input.next();

        productsImplementation.editProduct(columnToUpdate, productId, newValue); // Method Call To Edit Products
    }

   
}
