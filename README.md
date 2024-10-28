# Slaughterhouse Management System - gRPC Overview

This system relies on **gRPC** for efficient, high-performance communication across the three main stations in the animal processing workflow. By leveraging gRPC, we achieve:

- **Fast, Scalable Communication**: gRPC’s lightweight and high-performance protocol supports real-time data exchange between distributed stations.
- **Reliable Data Integrity**: With strict type definitions, gRPC ensures that all data transferred remains consistent across each station.
- **Ease of Integration**: gRPC makes it easy to add additional stations or extend functionality with minimal impact on the existing system.

## Implementation Summary

Each station operates as an independent service with a well-defined API to perform specific tasks in the processing workflow. gRPC methods at each station are responsible for handling unique aspects of the workflow, such as registering animals, managing parts, and recording products and distribution details.

The **`.proto`** files define each service’s API and message structures, making this the backbone of the gRPC implementation. Key points of the `.proto` file process include:

1. **Service Definition**: Each station has a dedicated `.proto` file that describes its gRPC services and their available methods. For example:
   - **Station 1** defines methods like `registerAnimal` and `listRegisteredAnimals`.
   - **Station 2** includes `addAnimalPart` and `getAnimalParts`.
   - **Station 3** includes `registerProduct` and `createOrder`.

2. **Automatic Code Generation**: The `.proto` files generate Java stubs, which are then implemented to handle gRPC calls. These stubs create a contract between services, ensuring consistent data types and function signatures across all stations.

3. **Inter-service Communication**: The generated code allows each station to act as both a gRPC server and client, facilitating seamless data flow and orchestration across the system.

4. **Hibernate Integration**: Each station employs **Hibernate** as the Object-Relational Mapping (ORM) framework to manage data persistence. This integration enables efficient data access and manipulation, leveraging Hibernate's capabilities to map Java objects to database tables, ensuring data consistency and integrity throughout the workflow.

5. **Maven Compilation**: To compile the project and ensure all dependencies are correctly set up, run Maven compile commands in each module's directory. Use the following command in your terminal:

   ```bash
   mvn clean compile
