package org.example.station3_productregistration.client;

import org.example.station3_productregistration.server.ProductRegistrationRMI;
import org.example.station3_productregistration.server.ProductTrays;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class ProductRegistrationRMIClient {
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        // Lookup the remote service
        ProductRegistrationRMI service = (ProductRegistrationRMI) Naming.lookup("//localhost/ProductService");

        // Initialize scanner for user input
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nProduct Registration Client Menu:");
            System.out.println("1. Register Product");
            System.out.println("2. View Product Trays");
            System.out.println("3. Get Product Details");
            System.out.println("4. Assign Tray to Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1: // Register Product
                    System.out.print("Enter Product ID: ");
                    int newProductId = scanner.nextInt(); // Changed variable name
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter Product Type: ");
                    String productType = scanner.nextLine();
                    try {
                        service.registerProduct(newProductId, productType);
                        System.out.println("Product registered successfully.");
                    } catch (RemoteException e) {
                        System.out.println("Error registering product: " + e.getMessage());
                    }
                    break;

                case 2: // View Product Trays
                    List<ProductTrays> productTrays = service.getAllProductTrays();
                    System.out.println("Registered Product Trays:");
                    for (ProductTrays tray : productTrays) {
                        System.out.println(tray); // Assuming each tray has a proper toString implementation
                    }
                    break;

                case 3: // Get Product Details
                    System.out.print("Enter Product ID to view details: ");
                    int detailsProductId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    try {
                        List<String> productDetails = service.getProductById(detailsProductId);
                        System.out.println("Product Details for ID " + detailsProductId + ": ");
                        for (String detail : productDetails) {
                            System.out.println(detail);
                        }
                    } catch (RemoteException e) {
                        System.out.println("Error fetching product details: " + e.getMessage());
                    }
                    break;

                case 4: // Assign Tray to Product
                    System.out.print("Enter Tray ID: ");
                    int trayId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Product ID: ");
                    int targetProductId = scanner.nextInt(); // Changed variable name
                    scanner.nextLine(); // Consume newline
                    try {
                        service.assignTrayToProduct(trayId, targetProductId);
                        System.out.println("Tray successfully assigned to product.");
                    } catch (RemoteException e) {
                        System.out.println("Error assigning tray to product: " + e.getMessage());
                    }
                    break;

                case 5: // Exit
                    exit = true;
                    System.out.println("Exiting the client...");
                    break;

                default:
                    System.out.println("Invalid choice. Please select between 1 and 5.");
                    break;
            }
        }

        scanner.close();
    }
}
