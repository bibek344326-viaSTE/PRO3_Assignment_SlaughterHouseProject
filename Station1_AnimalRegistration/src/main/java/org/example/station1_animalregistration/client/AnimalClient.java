package org.example.station1_animalregistration.client;

import org.example.station1_animalregistration.server.AnimalRegistrationRMI;

import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class AnimalClient {
    public static void main(String[] args) {
        try {
            // Lookup the remote object from the RMI registry
            AnimalRegistrationRMI server = (AnimalRegistrationRMI) Naming.lookup("rmi://localhost/AnimalRegistrationService");

            // Create a scanner for reading user input
            Scanner scanner = new Scanner(System.in);

            // Main loop to choose between registering a new animal or listing registered animals
            while (true) {
                System.out.println("Enter 'register' to register a new animal or 'list' to list all animals (or 'exit' to quit):");
                String command = scanner.nextLine().toLowerCase();  // Convert input to lowercase to make input case-insensitive

                if (command.equals("register")) {
                    // Register a new animal
                    System.out.print("Enter Animal Weight (kg): ");
                    double weight = scanner.nextDouble();

                    // Clear the newline character from input buffer after using nextDouble()
                    scanner.nextLine();

                    // Register the animal via RMI (server will generate registration number)
                    server.registerAnimal(weight);
                    System.out.println("Animal registered successfully!");

                } else if (command.equals("list")) {
                    // List all registered animals
                    List<String> animals = server.listRegisteredAnimals();
                    if (animals.isEmpty()) {
                        System.out.println("No animals registered.");
                    } else {
                        System.out.println("Registered animals:");
                        for (String animal : animals) {
                            System.out.println(animal);
                        }
                    }

                } else if (command.equals("exit")) {
                    // Exit the program
                    System.out.println("Exiting the system.");
                    break;

                } else {
                    System.out.println("Invalid command. Please enter 'register', 'list', or 'exit'.");
                }
            }

        } catch (Exception e) {
            System.err.println("RMI Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
