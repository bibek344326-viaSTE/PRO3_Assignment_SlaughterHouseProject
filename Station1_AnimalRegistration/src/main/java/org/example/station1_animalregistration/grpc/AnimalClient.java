package org.example.station1_animalregistration.grpc;

import com.animalregistration.AnimalRequest;
import com.animalregistration.AnimalResponse;
import com.animalregistration.AnimalListResponse;
import com.animalregistration.AnimalRegistrationServiceGrpc;
import com.animalregistration.EmptyRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class AnimalClient {
    public static void main(String[] args) {
        // Create a channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext() // Disable SSL for simplicity
                .build();

        // Create a stub for the service
        AnimalRegistrationServiceGrpc.AnimalRegistrationServiceBlockingStub stub =
                AnimalRegistrationServiceGrpc.newBlockingStub(channel);

        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Main loop for user interaction
        while (true) {
            System.out.println("Enter 'register' to register a new animal or 'list' to list all animals (or 'exit' to quit):");
            String command = scanner.nextLine().toLowerCase();

            if (command.equals("register")) {
                // Register a new animal
                System.out.print("Enter Animal Weight (kg): ");
                double weight = scanner.nextDouble();
                scanner.nextLine(); // Clear newline

                AnimalRequest request = AnimalRequest.newBuilder()
                        .setWeight(weight)
                        .build();

                // Call the remote method to register the animal
                AnimalResponse response = stub.registerAnimal(request);
                System.out.println("Registration Number: " + response.getMessage());

            } else if (command.equals("list")) {
                // List all registered animals
                EmptyRequest emptyRequest = EmptyRequest.newBuilder().build();
                AnimalListResponse animalListResponse = stub.listRegisteredAnimals(emptyRequest);

                System.out.println("Registered animals:");
                for (var animal : animalListResponse.getAnimalsList()) {
                    System.out.printf("ID: %d, Registration Number: %s, Weight: %.2f kg, Registration Date: %s%n",
                            animal.getAnimalId(),
                            animal.getRegistrationNumber(),
                            animal.getWeight(),
                            animal.getRegistrationDate());
                }

            } else if (command.equals("exit")) {
                System.out.println("Exiting the system.");
                break;

            } else {
                System.out.println("Invalid command. Please enter 'register', 'list', or 'exit'.");
            }
        }

        // Shutdown the channel
        channel.shutdown();
    }
}
