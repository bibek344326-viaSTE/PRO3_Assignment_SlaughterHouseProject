package org.example.station2_cuttingandpartsregistration.Client;

import com.cutting_partsRegistration.*;
import com.cutting_partsRegistration.CuttingAndPartRegistrationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;
import java.util.Scanner;

public class CuttingAndPartRegistrationClient {
    public static void main(String[] args) {
        // Establish a gRPC channel to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();

        // Create a stub for accessing the service
        CuttingAndPartRegistrationServiceGrpc.CuttingAndPartRegistrationServiceBlockingStub serviceStub =
                CuttingAndPartRegistrationServiceGrpc.newBlockingStub(channel);

        // Initialize scanner for user input
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nSlaughterhouse Client Menu:");
            System.out.println("1. Add Animal Part");
            System.out.println("2. View Animal Parts");
            System.out.println("3. Add Tray");
            System.out.println("4. Add Part to Tray");
            System.out.println("5. View Trays");
            System.out.println("6. View Parts in Tray");
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1-7): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            try {
                switch (choice) {
                    case 1: // Add Animal Part
                        System.out.print("Enter Animal ID: ");
                        int animalId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline

                        System.out.print("Enter Part Type (e.g., Leg, Shoulder): ");
                        String partType = scanner.nextLine();

                        System.out.print("Enter Part Weight: ");
                        double weight = scanner.nextDouble();
                        scanner.nextLine();

                        AddAnimalPartRequest addAnimalPartRequest = AddAnimalPartRequest.newBuilder()
                                .setAnimalId(animalId)
                                .setPartType(partType)
                                .setWeight(weight)
                                .build();
                        AddAnimalPartResponse addAnimalPartResponse = serviceStub.addAnimalPart(addAnimalPartRequest);
                        System.out.println("Animal part added: " + addAnimalPartResponse.getSuccess());
                        break;

                    case 2: // View Animal Parts
                        System.out.print("Enter Animal ID to view parts: ");
                        int animalIdForParts = scanner.nextInt();
                        GetAnimalPartsRequest getAnimalPartsRequest = GetAnimalPartsRequest.newBuilder()
                                .setAnimalId(animalIdForParts)
                                .build();
                        GetAnimalPartsResponse getAnimalPartsResponse = serviceStub.getAnimalParts(getAnimalPartsRequest);
                        System.out.println("Animal Parts for Animal ID " + animalIdForParts + ": " + getAnimalPartsResponse.getPartsList());
                        break;

                    case 3: // Add Tray
                        System.out.print("Enter Part Type for Tray (e.g., Leg, Shoulder): ");
                        String trayPartType = scanner.nextLine();

                        System.out.print("Enter Tray Max Weight Capacity: ");
                        double trayMaxWeight = scanner.nextDouble();

                        AddTrayRequest addTrayRequest = AddTrayRequest.newBuilder()
                                .setPartType(trayPartType)
                                .setMaxWeightCapacity(trayMaxWeight)
                                .build();
                        AddTrayResponse addTrayResponse = serviceStub.addTray(addTrayRequest);
                        System.out.println("Tray added: " + addTrayResponse.getSuccess());
                        break;

                    case 4: // Add Part to Tray
                        System.out.print("Enter Tray ID: ");
                        int trayId = scanner.nextInt();

                        System.out.print("Enter Part ID to add to Tray: ");
                        int partId = scanner.nextInt();

                        AddPartToTrayRequest addPartToTrayRequest = AddPartToTrayRequest.newBuilder()
                                .setTrayId(trayId)
                                .setPartId(partId)
                                .build();
                        AddPartToTrayResponse addPartToTrayResponse = serviceStub.addPartToTray(addPartToTrayRequest);
                        System.out.println("Part added to tray: " + addPartToTrayResponse.getSuccess());
                        break;

                    case 5: // View Trays
                        GetTraysRequest getTraysRequest = GetTraysRequest.newBuilder().build();
                        GetTraysResponse getTraysResponse = serviceStub.getTrays(getTraysRequest);
                        System.out.println("Trays: " + getTraysResponse.getTraysList());
                        break;

                    case 6: // View Parts in a Tray
                        System.out.print("Enter Tray ID to view parts: ");
                        int trayIdForParts = scanner.nextInt();
                        GetPartsInTrayRequest getPartsInTrayRequest = GetPartsInTrayRequest.newBuilder()
                                .setTrayId(trayIdForParts)
                                .build();
                        GetPartsInTrayResponse getPartsInTrayResponse = serviceStub.getPartsInTray(getPartsInTrayRequest);
                        System.out.println("Parts in Tray ID " + trayIdForParts + ": " + getPartsInTrayResponse.getPartsList());
                        break;

                    case 7: // Exit
                        exit = true;
                        System.out.println("Exiting the client...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please select between 1 and 7.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
        channel.shutdown();
    }
}
