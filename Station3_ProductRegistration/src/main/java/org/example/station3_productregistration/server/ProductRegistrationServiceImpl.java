package org.example.station3_productregistration.server;

import io.grpc.stub.StreamObserver;
import org.example.station3_productregistration.grpc.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRegistrationServiceImpl extends ProductRegistrationServiceGrpc.ProductRegistrationServiceImplBase {
    private Connection connection;

    public ProductRegistrationServiceImpl() {
        try {
            // Establish a connection to the PostgreSQL database
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=slaughterhousedb", "postgres", "Sneha123");
        } catch (SQLException e) {
            e.printStackTrace();
        }    }

    @Override
    public void registerProduct(RegisterProductRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        String query = "INSERT INTO products (product_id, product_type) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, request.getProductId());
            stmt.setString(2, request.getProductType());
            stmt.executeUpdate();
            System.out.println("Product registered successfully.");
            responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (SQLException e) {
            responseObserver.onError(new RuntimeException("Error while registering product: " + e.getMessage()));
        }
    }

    @Override
    public void getProductById(GetProductByIdRequest request, StreamObserver<GetProductByIdResponse> responseObserver) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, request.getProductId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Product product = Product.newBuilder()
                        .setProductId(rs.getInt("product_id"))
                        .setProductType(rs.getString("product_type"))
                        .build();
                GetProductByIdResponse response = GetProductByIdResponse.newBuilder()
                        .setProduct(product)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(new RuntimeException("Product not found for ID: " + request.getProductId()));
            }
        } catch (SQLException e) {
            responseObserver.onError(new RuntimeException("Error while fetching product details: " + e.getMessage()));
        }
    }

    @Override
    public void getAllProductTrays(com.google.protobuf.Empty request, StreamObserver<GetAllProductTraysResponse> responseObserver) {
        String query = "SELECT * FROM product_trays";
        List<ProductTrays> trays = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Ensure this references the correct Protobuf generated class
                ProductTrays tray = ProductTrays.newBuilder()
                        .setProductId(rs.getInt("product_id"))
                        .setTrayId(rs.getInt("tray_id"))
                        .build();
                trays.add(tray);
            }
            // Ensure the response is built correctly
            GetAllProductTraysResponse response = GetAllProductTraysResponse.newBuilder()
                    .addAllTrays(trays)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (SQLException e) {
            responseObserver.onError(new RuntimeException("Error while fetching product trays: " + e.getMessage()));
        }
    }


    @Override
    public void getAllProductTypes(com.google.protobuf.Empty request, StreamObserver<GetAllProductTypesResponse> responseObserver) {
        String query = "SELECT DISTINCT product_type FROM products";
        List<String> productTypes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                productTypes.add(rs.getString("product_type"));
            }
            GetAllProductTypesResponse response = GetAllProductTypesResponse.newBuilder()
                    .addAllProductTypes(productTypes)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (SQLException e) {
            responseObserver.onError(new RuntimeException("Error while fetching product types: " + e.getMessage()));
        }
    }

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        String query = "INSERT INTO order_products (order_id, product_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, request.getOrderId());
            stmt.setInt(2, request.getProductId());
            stmt.executeUpdate();
            System.out.println("Order created successfully.");
            responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (SQLException e) {
            responseObserver.onError(new RuntimeException("Error while creating order: " + e.getMessage()));
        }
    }

    @Override
    public void getOrderById(GetOrderByIdRequest request, StreamObserver<GetOrderByIdResponse> responseObserver) {
        String query = "SELECT * FROM order_products WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, request.getOrderId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                OrderProducts orderProducts = OrderProducts.newBuilder()
                        .setOrderId(rs.getInt("order_id"))
                        .setProductId(rs.getInt("product_id"))
                        .build();
                GetOrderByIdResponse response = GetOrderByIdResponse.newBuilder()
                        .setOrderProducts(orderProducts)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(new RuntimeException("Order not found for ID: " + request.getOrderId()));
            }
        } catch (SQLException e) {
            responseObserver.onError(new RuntimeException("Error while fetching order details: " + e.getMessage()));
        }
    }

    @Override
    public void createDistributionOrder(CreateDistributionOrderRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        String query = "INSERT INTO distribution_orders (order_id, customer_details, shipping_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, request.getOrderId());
            stmt.setString(2, request.getCustomerDetails());
            stmt.setString(3, request.getShippingDate());
            stmt.executeUpdate();
            System.out.println("Distribution order created successfully.");
            responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (SQLException e) {
            responseObserver.onError(new RuntimeException("Error while creating distribution order: " + e.getMessage()));
        }
    }

    @Override
    public void getDistributionOrderById(GetDistributionOrderByIdRequest request, StreamObserver<GetDistributionOrderByIdResponse> responseObserver) {
        String query = "SELECT * FROM distribution_orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, request.getOrderId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DistributionOrder distributionOrder = DistributionOrder.newBuilder()
                        .setOrderId(rs.getInt("order_id"))
                        .setCustomerDetails(rs.getString("customer_details"))
                        .setShippingDate(rs.getString("shipping_date")) // Assuming shipping_date is a string
                        .setRecallStatus(rs.getBoolean("recall_status"))
                        .build();
                GetDistributionOrderByIdResponse response = GetDistributionOrderByIdResponse.newBuilder()
                        .setDistributionOrder(distributionOrder)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(new RuntimeException("Distribution order not found for ID: " + request.getOrderId()));
            }
        } catch (SQLException e) {
            responseObserver.onError(new RuntimeException("Error while fetching distribution order details: " + e.getMessage()));
        }
    }
}
