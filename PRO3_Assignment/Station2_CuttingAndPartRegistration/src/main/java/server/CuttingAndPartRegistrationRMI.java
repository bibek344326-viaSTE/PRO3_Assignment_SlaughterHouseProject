package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CuttingAndPartRegistrationRMI extends Remote {
    // Method to add animal parts
    void addAnimalPart(int animalId, String partType, double weight) throws RemoteException;

    // Method to retrieve all parts for a specific animal
    List<String> getAnimalParts(int animalId) throws RemoteException;

    // Method to add a new tray
    void addTray(String partType, double maxWeightCapacity) throws RemoteException;

    // Method to add a part to a tray
    void addPartToTray(int trayId, int partId) throws RemoteException;

    // Method to retrieve all trays
    List<String> getTrays() throws RemoteException;

    // Method to get parts in a specific tray
    List<String> getPartsInTray(int trayId) throws RemoteException;
}
