package org.example.station3_productregistration.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductRegistrationRMI extends Remote {

    /**
     * Registers a new product in the system.
     *
     * @param productId   the unique identifier of the product
     * @param productType the type/category of the product
     * @throws RemoteException if there is an error during remote method invocation
     */
    void registerProduct(int productId, String productType) throws RemoteException;

    /**
     * Retrieves the details of a product by its ID.
     *
     * @param productId the ID of the product
     * @return a list of strings representing the product details
     * @throws RemoteException if there is an error during remote method invocation
     */
    List<String> getProductById(int productId) throws RemoteException;

    /**
     * Retrieves all product trays in the system.
     *
     * @return a list of ProductTrays objects
     * @throws RemoteException if there is an error during remote method invocation
     */
    List<ProductTrays> getAllProductTrays() throws RemoteException;

    /**
     * Assigns a tray to a product.
     *
     * @param trayId    the ID of the tray
     * @param productId the ID of the product
     * @throws RemoteException if there is an error during remote method invocation
     */
    void assignTrayToProduct(int trayId, int productId) throws RemoteException;

}
