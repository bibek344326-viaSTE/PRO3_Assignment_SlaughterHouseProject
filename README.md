# Slaughterhouse Management System - gRPC Overview

This system relies on **gRPC** for efficient, high-performance communication across the three main stations in the animal processing workflow. By leveraging gRPC, we achieve:

- **Fast, Scalable Communication**: gRPC’s lightweight and high-performance protocol supports real-time data exchange between distributed stations.
- **Reliable Data Integrity**: With strict type definitions, gRPC ensures that all data transferred remains consistent across each station.
- **Ease of Integration**: gRPC makes it easy to add additional stations or extend functionality with minimal impact on the existing system.

## Implementation Summary

Each station operates as an independent service with a well-defined API to perform specific tasks in the processing workflow. gRPC methods at each station are responsible for handling unique aspects of the workflow, such as registering animals, managing parts, and recording products and distribution details.

The **`.proto`** files define each service’s API and message structures, making this the backbone of the gRPC implementation. Key points of the `.proto` file process include:

### Service Definition

Each station has a dedicated `.proto` file that describes its gRPC services and their available methods. For example:

- **Station 1** defines methods like `registerAnimal` and `listRegisteredAnimals`.
- **Station 2** includes `addAnimalPart` and `getAnimalParts`.
- **Station 3** includes `registerProduct` and `createOrder`.

### Automatic Code Generation

The `.proto` files generate Java stubs, which are then implemented to handle gRPC calls. These stubs create a contract between services, ensuring consistent data types and function signatures across all stations.

### Inter-service Communication

The generated code allows each station to act as both a gRPC server and client, facilitating seamless data flow and orchestration across the system.

### Hibernate Integration

Each station employs **Hibernate** as the Object-Relational Mapping (ORM) framework to manage data persistence. This integration enables efficient data access and manipulation, leveraging Hibernate's capabilities to map Java objects to database tables, ensuring data consistency and integrity throughout the workflow.

## Setup Instructions

### Database Setup

1. **Configure the Database**: Ensure you have a database setup with the necessary tables and relationships specific to each module (Station 1, 2, and 3). Each module relies on distinct tables, so you’ll need to configure the schema according to the module requirements.
2. **Hibernate Configuration**: Each module is set up with Hibernate for ORM. Ensure your `application.properties` or `application.yml` files contain the correct database credentials.

### Compiling and Running the Application

1. **Clone and Fetch Dependencies**: After cloning the repository and fetching dependencies, navigate to each module directory and run the Maven compile command to generate necessary files.

   ```bash
   mvn clean compile
   
### Handle Generated Sources

After running `mvn compile`, you will find a `target` folder inside each module directory. Open the `target/generated-sources` directory, locate the `protobuf` folder, and mark it as a *Generated Source Root*. This allows IntelliJ (or your IDE) to recognize these files and remove any unresolved errors.

### Run the Application

1. **Start the gRPC server** in each module by running the main application class.
2. You can **test the client application** by running the provided gRPC client files within each module.

### Client Testing

After starting each station’s server, run the gRPC client within each station module to test the methods:

- **Station 1**: Test animal registration methods.
- **Station 2**: Verify part management.
- **Station 3**: Check product registration and distribution order methods.
