package org.example.station2_cuttingandpartsregistration.Server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CuttingAndPartRegistrationRMIServer extends UnicastRemoteObject implements CuttingAndPartRegistrationRMI {
    private Connection connection;


    //Constructor
    protected CuttingAndPartRegistrationRMIServer() throws RemoteException {
        super();
        try {
            // Connect to PostgreSQL database
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=slaughterhousedb", "postgres", "Sneha123");
            System.out.println("Connected to the PostgreSQL database.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Could not connect to the database.");
        }
    }

    // Add a new animal part
    @Override
    public void addAnimalPart(int animalId, String partType, double weight) throws RemoteException {
        String query = "INSERT INTO animal_parts (animal_id, part_type, weight) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, animalId);
            pstmt.setString(2, partType);
            pstmt.setDouble(3, weight);
            pstmt.executeUpdate();
            System.out.println("Added part: " + partType + " for animal ID: " + animalId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error adding animal part.");
        }
    }

    // Get all parts for an animal
    @Override
public List<String> getAnimalParts(int animalId) throws RemoteException {
    System.out.println("Fetching parts for animal ID: " + animalId); // Debugging output
    String query = "SELECT part_id, part_type, weight FROM animal_parts WHERE animal_id = ?";
    List<String> partDescriptions = new ArrayList<>();
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setInt(1, animalId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int partId = rs.getInt("part_id");
            String partType = rs.getString("part_type");
            double weight = rs.getDouble("weight");
            partDescriptions.add("PartID: " + partId + ", Type: " + partType + ", Weight: " + weight);
            System.out.println("Fetched part: " + partType + " with weight: " + weight); // Debugging output
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RemoteException("Error retrieving animal parts.");
    }
    return partDescriptions;
}


    // Add a new tray
    @Override
    public void addTray(String partType, double maxWeightCapacity) throws RemoteException {
        String query = "INSERT INTO trays (part_type, max_weight_capacity) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, partType);
            pstmt.setDouble(2, maxWeightCapacity);
            pstmt.executeUpdate();
            System.out.println("Added tray: " + partType + " with max weight: " + maxWeightCapacity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error adding tray.");
        }
    }

    // Add a part to a tray
    // Add a part to a tray
    @Override
    public void addPartToTray(int trayId, int partId) throws RemoteException {
        String query = "INSERT INTO tray_parts (tray_id, part_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, trayId);
            pstmt.setInt(2, partId);
            pstmt.executeUpdate();
            System.out.println("Added part ID: " + partId + " to tray ID: " + trayId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error adding part to tray.");
        }
    }

    // Get all trays
    @Override
    public List<String> getTrays() throws RemoteException {
        String query = "SELECT tray_id, part_type, max_weight_capacity FROM trays";
        List<String> trayDescriptions = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int trayId = rs.getInt("tray_id");
                String partType = rs.getString("part_type");
                double maxCapacity = rs.getDouble("max_weight_capacity");
                trayDescriptions.add("TrayID: " + trayId + ", Type: " + partType + ", Max Capacity: " + maxCapacity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error retrieving trays.");
        }
        return trayDescriptions;
    }


     // Get parts in a specific tray
    @Override
    public List<String> getPartsInTray(int trayId) throws RemoteException {
        String query = "SELECT p.part_id, p.part_type, p.weight FROM tray_parts tp " +
                "JOIN animal_parts p ON tp.part_id = p.part_id WHERE tp.tray_id = ?";
        List<String> partDescriptions = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, trayId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int partId = rs.getInt("part_id");
                String partType = rs.getString("part_type");
                double weight = rs.getDouble("weight");
                partDescriptions.add("PartID: " + partId + ", Type: " + partType + ", Weight: " + weight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error retrieving parts in tray.");
        }
        return partDescriptions;
    }

    // Main method to start the server
    public static void main(String[] args) {
        try {
            CuttingAndPartRegistrationRMI server = new CuttingAndPartRegistrationRMIServer();
            java.rmi.registry.LocateRegistry.createRegistry(1099); // Start the RMI registry
            Naming.rebind("SlaughterhouseService", server); // Bind the service
            System.out.println("Slaughterhouse RMI Server is running with database integration...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
