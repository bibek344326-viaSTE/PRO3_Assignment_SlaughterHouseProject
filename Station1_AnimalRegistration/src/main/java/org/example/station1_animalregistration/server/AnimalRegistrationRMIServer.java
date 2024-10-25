package org.example.station1_animalregistration.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalRegistrationRMIServer extends UnicastRemoteObject implements AnimalRegistrationRMI {
    private Connection connection;

    // Constructor - Connect to the database
    protected AnimalRegistrationRMIServer() throws RemoteException {
        super();
        try {
            Class.forName("org.postgresql.Driver");  // Load PostgreSQL driver
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres?currentSchema=slaughterhousedb",
                    "postgres", "Sneha123"
            );
            System.out.println("Connected to the PostgreSQL database.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RemoteException("PostgreSQL JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Could not connect to the database.");
        }
    }

    // Register a new animal in the database
    @Override
    public void registerAnimal(double weight) throws RemoteException {
        try {
            String registrationNumber = getNextRegistrationNumber();  // Generate the next registration number
            String query = "INSERT INTO animals (registration_number, weight, registration_date) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, registrationNumber);  // Set the registration number
                pstmt.setDouble(2, weight);              // Set the animal's weight
                pstmt.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));  // Set registration date
                pstmt.executeUpdate();                   // Execute the insert statement
                System.out.println("Registered animal with weight: " + weight + " and registration number: " + registrationNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error registering animal.");
        }
    }

    // List all registered animals
    @Override
    public List<String> listRegisteredAnimals() throws RemoteException {
        String query = "SELECT animal_id, registration_number, weight, registration_date FROM animals";
        List<String> animalDescriptions = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int animalId = rs.getInt("animal_id");
                String regInfo = rs.getString("registration_number");
                double weight = rs.getDouble("weight");
                Timestamp regDate = rs.getTimestamp("registration_date");
                animalDescriptions.add("AnimalID: " + animalId + ", Registration: " + regInfo + ", Weight: " + weight + ", Date: " + regDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error listing animals.");
        }
        return animalDescriptions;
    }

    // Generate the next registration number based on the most recent one in the database
    private String getNextRegistrationNumber() throws SQLException {
        String query = "SELECT registration_number FROM animals ORDER BY animal_id DESC LIMIT 1";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String lastRegNumber = rs.getString("registration_number");
                return incrementRegistrationNumber(lastRegNumber);  // Increment the registration number
            } else {
                return "REG00001";  // Return the first registration number if no animals are registered
            }
        }
    }

    // Increment the registration number (e.g., from REG12345 to REG12346)
    private String incrementRegistrationNumber(String currentNumber) {
        String prefix = currentNumber.substring(0, 3);  // "REG"
        int number = Integer.parseInt(currentNumber.substring(3));  // "12345" -> 12345
        number++;
        return prefix + String.format("%05d", number);  // Increment and zero-pad (e.g., "REG12346")
    }

    // Main method to start the server
    public static void main(String[] args) {
        try {
            AnimalRegistrationRMI server = new AnimalRegistrationRMIServer();
            java.rmi.registry.LocateRegistry.createRegistry(1099);  // Start the RMI registry
            Naming.rebind("AnimalRegistrationService", server);  // Bind the service
            System.out.println("Animal Registration RMI Server is running with database integration...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
