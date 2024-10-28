package org.example.station1_animalregistration.grpc;

import com.animalregistration.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;

@Service
public class AnimalRegistrationServiceImpl extends AnimalRegistrationServiceGrpc.AnimalRegistrationServiceImplBase {
    private Connection connection;

    public AnimalRegistrationServiceImpl() throws SQLException, ClassNotFoundException {
        // Connect to the database
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=slaughterhousedb",
                "postgres", "Sneha123"
        );
    }
    @Override
    public void registerAnimal(AnimalRequest request, StreamObserver<AnimalResponse> responseObserver) {
        try {
            String registrationNumber = getNextRegistrationNumber();
            String query = "INSERT INTO animals (registration_number, weight, registration_date) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, registrationNumber);
                pstmt.setDouble(2, request.getWeight());
                pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();
            }
            AnimalResponse response = AnimalResponse.newBuilder()
                    .setMessage(registrationNumber)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (SQLException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void listRegisteredAnimals(EmptyRequest request, StreamObserver<AnimalListResponse> responseObserver) {
        AnimalListResponse.Builder responseBuilder = AnimalListResponse.newBuilder();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT animal_id, registration_number, weight, registration_date FROM animals");
            while (rs.next()) {
                responseBuilder.addAnimals(Animal.newBuilder()
                        .setAnimalId(rs.getInt("animal_id"))
                        .setRegistrationNumber(rs.getString("registration_number"))
                        .setWeight(rs.getDouble("weight"))
                        .setRegistrationDate(rs.getTimestamp("registration_date").toString())
                        .build());
            }
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (SQLException e) {
            responseObserver.onError(e);
        }
    }
    private String getNextRegistrationNumber() throws SQLException {
        String query = "SELECT registration_number FROM animals ORDER BY animal_id DESC LIMIT 1";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String lastRegNumber = rs.getString("registration_number");
                return incrementRegistrationNumber(lastRegNumber);
            } else {
                return "REG00001"; // First registration number
            }
        }
    }

    private String incrementRegistrationNumber(String currentNumber) {
        String prefix = currentNumber.substring(0, 3);
        int number = Integer.parseInt(currentNumber.substring(3));
        number++;
        return prefix + String.format("%05d", number);
    }
}
