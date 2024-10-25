package org.example.station1_animalregistration.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AnimalRegistrationRMI extends Remote {
    void registerAnimal(double weight) throws RemoteException;
    List<String> listRegisteredAnimals() throws RemoteException;
}
