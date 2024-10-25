package org.example.station3_productregistration.server.database;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=slaughterhousedb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "rachu1997";

    private Connection connection;

    public DatabaseConnection() throws RemoteException {
        try {
            // Connect to PostgreSQL database

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=slaughterhousedb", USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL database.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Could not connect to the database.");
        }
    }
    // Expose the connection for other classes to use
    public Connection getConnection() {
        return connection;
    }
}