package org.example.station2_cuttingandpartsregistration.Client;

import org.example.station2_cuttingandpartsregistration.Server.CuttingAndPartRegistrationRMI;

import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class CuttingAndPartRegistrationRMIClient {
    public static void main(String[] args) {
                              try {
            CuttingAndPartRegistrationRMI service = (CuttingAndPartRegistrationRMI) Naming.lookup("//localhost/SlaughterhouseService");

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

                        service.addAnimalPart(animalId, partType, weight);
                        System.out.println("Animal part added.");
                        break;

                    case 2: // View Animal Parts
                        System.out.print("Enter Animal ID to view parts: ");
                        int animalIdForParts = scanner.nextInt();
                        List<String> parts = service.getAnimalParts(animalIdForParts);
                        System.out.println("Animal Parts for Animal ID " + animalIdForParts + ": " + parts);
                        break;

                    case 3: // Add Tray
                        System.out.print("Enter Part Type for Tray (e.g., Leg, Shoulder): ");
                        String trayPartType = scanner.nextLine();

                        System.out.print("Enter Tray Max Weight Capacity: ");
                        double trayMaxWeight = scanner.nextDouble();

                        service.addTray(trayPartType, trayMaxWeight);
                        System.out.println("Tray added.");
                        break;

                    case 4: // Add Part to Tray
                        System.out.print("Enter Tray ID: ");
                        int trayId = scanner.nextInt();

                        System.out.print("Enter Part ID to add to Tray: ");
                        int partId = scanner.nextInt();

                        service.addPartToTray(trayId, partId);
                        System.out.println("Part added to tray.");
                        break;

                    case 5: // View Trays
                        List<String> trays = service.getTrays();
                        System.out.println("Trays: " + trays);
                        break;

                    case 6: // View Parts in a Tray
                        System.out.print("Enter Tray ID to view parts: ");
                        int trayIdForParts = scanner.nextInt();
                        List<String> partsInTray = service.getPartsInTray(trayIdForParts);
                        System.out.println("Parts in Tray ID " + trayIdForParts + ": " + partsInTray);
                        break;

                    case 7: // Exit
                        exit = true;
                        System.out.println("Exiting the client...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please select between 1 and 7.");
                        break;
                }
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
