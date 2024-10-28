package org.example.station3_productregistration.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.List;
import java.util.Scanner;

public class ProductRegistrationGRPCClient {
    public static void main(String[] args) {
        // Create a channel to connect to the gRPC server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // Use plaintext for simplicity; consider secure connections in production
                .build();
        ProductRegistrationServiceGrpc.ProductRegistrationServiceBlockingStub stub =
                ProductRegistrationServiceGrpc.newBlockingStub(channel);

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

                    CreateOrderRequest createOrderRequest = CreateOrderRequest.newBuilder()
                            .setOrderId(orderId)
                            .setProductId(productId)
                            .build();

                    try {
                        stub.createOrder(createOrderRequest);
                        System.out.println("Order created for Product ID " + productId + " with Order ID " + orderId);
                    } catch (StatusRuntimeException e) {
                        System.err.println("RPC failed: " + e.getStatus());
                    }
                    break;

                case 2: // View Product Trays
                    try {
                        GetAllProductTraysResponse response = stub.getAllProductTrays(com.google.protobuf.Empty.getDefaultInstance());
                        List<ProductTrays> productTrays = response.getTraysList(); // This should match the interface
                        System.out.println("Registered Product Trays:");
                        for (ProductTrays tray : productTrays) {
                            System.out.println("Product ID: " + tray.getProductId() + ", Tray ID: " + tray.getTrayId());
                        }
                    } catch (StatusRuntimeException e) {
                        System.err.println("RPC failed: " + e.getStatus());
                    }
                    break;

                case 3: // Get Product Details
                    System.out.print("Enter Product ID to view details: ");
                    int detailsProductId = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline

                    GetProductByIdRequest getProductByIdRequest = GetProductByIdRequest.newBuilder()
                            .setProductId(detailsProductId)
                            .build();

                    try {
                        GetProductByIdResponse response = stub.getProductById(getProductByIdRequest);
                        Product product = response.getProduct();
                        System.out.println("Product Details for ID " + detailsProductId + ": " +
                                "Type: " + product.getProductType());
                    } catch (StatusRuntimeException e) {
                        System.err.println("RPC failed: " + e.getStatus());
                    }
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
        channel.shutdown(); // Gracefully shut down the channel
    }
}
