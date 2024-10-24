package org.example.station3_productregistration.server;

import org.example.station3_productregistration.server.database.DatabaseConnection;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductRegistrationImpl extends UnicastRemoteObject implements ProductRegistrationRMI {

    private Connection connection;

    protected ProductRegistrationImpl() throws RemoteException {
        super();
        try {
            // Initialize database connection
            DatabaseConnection dbConnection = new DatabaseConnection();
            this.connection = dbConnection.getConnection(); // Retrieve the connection from DatabaseConnection class
        } catch (RemoteException e) {
            throw new RemoteException("Failed to connect to the database", e);
        }
    }

    @Override
    public void registerProduct(int productId, String productType) throws RemoteException {
        String query = "INSERT INTO products (product_id, product_type) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setString(2, productType);
            stmt.executeUpdate();
            System.out.println("Product registered successfully.");
        } catch (SQLException e) {
            throw new RemoteException("Error while registering product", e);
        }
    }

    @Override
    public List<String> getProductById(int productId) throws RemoteException {
        String query = "SELECT * FROM products WHERE product_id = ?";
        List<String> productList =new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               int product_id = rs.getInt("product_id");
               String product_type = rs.getString("product_type");
               productList.add("Product id: " + product_id + " Product type: "+ product_type);
            } else {
                throw new RemoteException("Product not found for ID: " + productId);
            }
        } catch (SQLException e) {
            throw new RemoteException("Error while fetching product details", e);
        }
        return productList;
    }


    @Override
    public List<ProductTrays> getAllProductTrays() throws RemoteException {
        String query = "SELECT * FROM product_trays";
        List<ProductTrays> trays = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                trays.add(new ProductTrays(rs.getInt("product_id"), rs.getInt("tray_id")));
            }
        } catch (SQLException e) {
            throw new RemoteException("Error while fetching product trays", e);
        }
        return trays;
    }

    @Override
    public List<ProductTrays> getAllProductTypes() throws RemoteException {
        String query = "SELECT DISTINCT product_type FROM products";
        List<ProductTrays> types = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                types.add(new ProductTrays(rs.getInt("product_id"), rs.getInt("tray_id")));  // Assuming tray_id can represent a tray for types.
            }
        } catch (SQLException e) {
            throw new RemoteException("Error while fetching product types", e);
        }
        return types;
    }


    @Override
    public void createOrder(int orderId, int productId) throws RemoteException {
        String query = "INSERT INTO order_products (order_id, product_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
            System.out.println("Order created successfully.");
        } catch (SQLException e) {
            throw new RemoteException("Error while creating order", e);
        }
    }

    @Override
    public OrderProducts getOrderById(int orderId) throws RemoteException {
        String query = "SELECT * FROM order_products WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new OrderProducts(rs.getInt("order_id"), rs.getInt("product_id"));
            } else {
                throw new RemoteException("Order not found for ID: " + orderId);
            }
        } catch (SQLException e) {
            throw new RemoteException("Error while fetching order details", e);
        }
    }

    @Override
    public void createDistributionOrder(int orderId, String customerDetails, String shippingDate) throws RemoteException {
        String query = "INSERT INTO distribution_orders (order_id, customer_details, shipping_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setString(2, customerDetails);
            stmt.setString(3, shippingDate);
            stmt.executeUpdate();
            System.out.println("Distribution order created successfully.");
        } catch (SQLException e) {
            throw new RemoteException("Error while creating distribution order", e);
        }
    }

    @Override
    public DistributionOrders getDistributionOrderById(int orderId) throws RemoteException {
        String query = "SELECT * FROM distribution_orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DistributionOrders(
                        rs.getInt("order_id"),
                        rs.getString("customer_details"),
                        rs.getDate("shipping_date"),
                        rs.getBoolean("recall_status")
                );
            } else {
                throw new RemoteException("Distribution order not found for ID: " + orderId);
            }
        } catch (SQLException e) {
            throw new RemoteException("Error while fetching distribution order details", e);
        }
    }
    public static void main(String[] args) {
        try {
            // Start RMI registry
            LocateRegistry.createRegistry(1099);
            ProductRegistrationRMI productService = new ProductRegistrationImpl();
            Naming.rebind("//localhost/ProductService", productService);
            System.out.println("ProductService bound in RMI registry.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}







