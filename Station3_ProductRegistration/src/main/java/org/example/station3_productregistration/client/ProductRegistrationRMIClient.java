package org.example.station3_productregistration.client;

import org.example.station3_productregistration.server.ProductRegistrationRMI;
import org.example.station3_productregistration.server.Products;
import org.example.station3_productregistration.server.ProductTrays;

import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class ProductRegistrationRMIClient {
    public static void main(String[] args) {
        try {
            // Lookup the remote service
            ProductRegistrationRMI service = (ProductRegistrationRMI) Naming.lookup("//localhost/ProductService");

            // Initialize scanner for user input
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("\nProduct Registration Client Menu:");
                System.out.println("1. Create Order");
                System.out.println("2. View Product Trays");
                System.out.println("3. Get Product Details");
                System.out.println("4. Exit");
                System.out.print("Enter your choice (1-4): ");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                switch (choice) {
                    case 1: // Create Order
                        System.out.print("Enter Product ID to create an order: ");
                        int productId = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline

                        System.out.print("Enter Order ID: ");
                        int orderId = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline

                        service.createOrder(orderId, productId);
                        System.out.println("Order created for Product ID " + productId + " with Order ID " + orderId);
                        break;

                    case 2: // View Product Trays
                        List<ProductTrays> productTrays = service.getAllProductTrays(); // Should match the interface
                        System.out.println("Registered Product Trays:");
                        for (ProductTrays tray : productTrays) {
                            System.out.println(tray); // Assuming each tray is a string representation
                        }
                        break;

                    case 3: // Get Product Details
                        System.out.print("Enter Product ID to view details: ");
                        int detailsProductId = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline

                        // Fetch product details using the appropriate service method
                        List<String> productDetails = service.getProductById(detailsProductId);
                        System.out.println("Product Details for ID " + detailsProductId + ": " + productDetails);
                        break;

                    case 4: // Exit
                        exit = true;
                        System.out.println("Exiting the client...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please select between 1 and 4.");
                        break;
                }
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}