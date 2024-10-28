package org.example.station2_cuttingandpartsregistration.grpc;

import com.cutting_partsRegistration.*;
import com.cutting_partsRegistration.CuttingAndPartRegistrationServiceGrpc;
import io.grpc.stub.StreamObserver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuttingAndPartsRegistrationServiceImpl extends CuttingAndPartRegistrationServiceGrpc.CuttingAndPartRegistrationServiceImplBase {

    private Connection connection;

    public CuttingAndPartsRegistrationServiceImpl() {
        try {
            // Establish a connection to the PostgreSQL database
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=slaughterhousedb", "postgres", "Sneha123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAnimalPart(AddAnimalPartRequest request, StreamObserver<AddAnimalPartResponse> responseObserver) {
        String query = "INSERT INTO animal_parts (animal_id, part_type, weight) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, request.getAnimalId());
            pstmt.setString(2, request.getPartType());
            pstmt.setDouble(3, request.getWeight());
            pstmt.executeUpdate();

            responseObserver.onNext(AddAnimalPartResponse.newBuilder().setSuccess(true).build());
        } catch (SQLException e) {
            e.printStackTrace();
            responseObserver.onNext(AddAnimalPartResponse.newBuilder().setSuccess(false).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getAnimalParts(GetAnimalPartsRequest request, StreamObserver<GetAnimalPartsResponse> responseObserver) {
        String query = "SELECT part_id, part_type, weight FROM animal_parts WHERE animal_id = ?";
        List<Part> parts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, request.getAnimalId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Part part = Part.newBuilder()
                        .setPartId(rs.getInt("part_id"))
                        .setPartType(rs.getString("part_type"))
                        .setWeight(rs.getDouble("weight"))
                        .build();
                parts.add(part);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        GetAnimalPartsResponse response = GetAnimalPartsResponse.newBuilder().addAllParts(parts).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addTray(AddTrayRequest request, StreamObserver<AddTrayResponse> responseObserver) {
        String query = "INSERT INTO trays (part_type, max_weight_capacity) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, request.getPartType());
            pstmt.setDouble(2, request.getMaxWeightCapacity());
            pstmt.executeUpdate();
            responseObserver.onNext(AddTrayResponse.newBuilder().setSuccess(true).build());
        } catch (SQLException e) {
            e.printStackTrace();
            responseObserver.onNext(AddTrayResponse.newBuilder().setSuccess(false).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void addPartToTray(AddPartToTrayRequest request, StreamObserver<AddPartToTrayResponse> responseObserver) {
        String query = "INSERT INTO tray_parts (tray_id, part_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, request.getTrayId());
            pstmt.setInt(2, request.getPartId());
            pstmt.executeUpdate();
            responseObserver.onNext(AddPartToTrayResponse.newBuilder().setSuccess(true).build());
        } catch (SQLException e) {
            e.printStackTrace();
            responseObserver.onNext(AddPartToTrayResponse.newBuilder().setSuccess(false).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getTrays(GetTraysRequest request, StreamObserver<GetTraysResponse> responseObserver) {
        String query = "SELECT tray_id, part_type, max_weight_capacity FROM trays";
        List<com.cutting_partsRegistration.Tray> trays = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                com.cutting_partsRegistration.Tray tray = com.cutting_partsRegistration.Tray.newBuilder()
                        .setTrayId(rs.getInt("tray_id"))
                        .setPartType(rs.getString("part_type"))
                        .setMaxWeightCapacity(rs.getDouble("max_weight_capacity"))
                        .build();
                trays.add(tray);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Build the response using the generated Tray class
        GetTraysResponse response = GetTraysResponse.newBuilder().addAllTrays(trays).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPartsInTray(GetPartsInTrayRequest request, StreamObserver<GetPartsInTrayResponse> responseObserver) {
        String query = "SELECT p.part_id, p.part_type, p.weight FROM tray_parts tp JOIN animal_parts p ON tp.part_id = p.part_id WHERE tp.tray_id = ?";
        List<Part> parts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, request.getTrayId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Part part = Part.newBuilder()
                        .setPartId(rs.getInt("part_id"))
                        .setPartType(rs.getString("part_type"))
                        .setWeight(rs.getDouble("weight"))
                        .build();
                parts.add(part);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        GetPartsInTrayResponse response = GetPartsInTrayResponse.newBuilder().addAllParts(parts).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
