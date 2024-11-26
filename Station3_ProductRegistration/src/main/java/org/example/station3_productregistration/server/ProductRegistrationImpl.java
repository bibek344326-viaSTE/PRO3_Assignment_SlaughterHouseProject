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
        List<String> productList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int product_id = rs.getInt("product_id");
                String product_type = rs.getString("product_type");
                productList.add("Product ID: " + product_id + ", Product Type: " + product_type);
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
    public void assignTrayToProduct(int trayId, int productId) throws RemoteException {
        try {
            String trayExistsQuery = "SELECT COUNT(*) AS count FROM trays WHERE tray_id = ?";
            try (PreparedStatement trayExistsStmt = connection.prepareStatement(trayExistsQuery)) {
                trayExistsStmt.setInt(1, trayId);
                ResultSet trayExistsRs = trayExistsStmt.executeQuery();
                if (trayExistsRs.next() && trayExistsRs.getInt("count") == 0) {
                    throw new RemoteException("Tray with ID " + trayId + " does not exist.");
                }
            }
            String productExistsQuery = "SELECT product_type FROM products WHERE product_id = ?";
            try (PreparedStatement productExistsStmt = connection.prepareStatement(productExistsQuery)) {
                productExistsStmt.setInt(1, productId);
                ResultSet productExistsRs = productExistsStmt.executeQuery();
                if (!productExistsRs.next()) {
                    throw new RemoteException("Product with ID " + productId + " does not exist.");
                }
            }

            String assignTrayQuery = "INSERT INTO product_trays (tray_id, product_id) VALUES (?, ?)";
            try (PreparedStatement assignTrayStmt = connection.prepareStatement(assignTrayQuery)) {
                assignTrayStmt.setInt(1, trayId);
                assignTrayStmt.setInt(2, productId);
                assignTrayStmt.executeUpdate();
                System.out.println("Tray with ID " + trayId + " successfully assigned to Product ID " + productId);
            }

        } catch (SQLException e) {
            throw new RemoteException("Error while assigning tray to product: " + e.getMessage(), e);
        }
    }


    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ProductRegistrationRMI productService = new ProductRegistrationImpl();
            Naming.rebind("//localhost/ProductService", productService);
            System.out.println("ProductService bound in RMI registry.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
