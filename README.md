# Slaughterhouse Management System

## Overview
This project simulates the operation of a slaughterhouse. The system is divided into three main stations, each of which handles a different part of the process. The stations are designed to work independently, ensuring that the process can continue even if one station is temporarily offline. External services can interact with the system using gRPC to retrieve relevant data.

### The Process:
1. **Station 1: Animal Registration**
   - Animals arrive at the slaughterhouse, are weighed, and registered with details like their weight, registration number, and origin (farm).
   
2. **Station 2: Cutting and Part Registration**
   - Animals are cut into smaller parts, each part is weighed and registered. Each part is linked back to the original animal and is stored in trays. Each tray contains only one type of part and has a maximum weight capacity.
   
3. **Station 3: Product Registration**
   - Parts are used to create products. These products may consist of parts from multiple animals or the same animal. All products are registered and linked to the trays the parts came from.

If there is a need to recall products due to an issue with an animal, the system provides a means to trace and recall all products that contain parts from the affected animal.

---

## Architecture Overview

The system consists of three stations that interact with a central **PostgreSQL database**. The stations communicate **independently**, and external systems can retrieve information via **gRPC**.

### Stations:

- **Station 1: Animal Registration** (RESTful API)
  - Registers animal data when animals arrive at the slaughterhouse.
  
- **Station 2: Cutting and Part Registration** (RMI)
  - Processes animals into parts and stores them in trays.
  
- **Station 3: Product Registration** (RMI)
  - Registers final products and links them to trays containing parts.

### External Communication:

- **gRPC Service**:
  - Provides an external interface to query animal and product data, enabling recalls if an issue with an animal is discovered.

### Communication Model:

- **Internal communication**:
  - Stations communicate with the **PostgreSQL database** independently.
  - **Station 2** and **Station 3** use **RMI** for internal communication.
  
- **External communication**:
  - External systems interact with the **gRPC service** to retrieve information about animals and products.

---

## Database Schema

The system uses a **PostgreSQL** database to store data related to animals, animal parts, trays, and products. The database schema is located in the `db_script.sql` file at the root of the project.

For setup, please execute the `db_script.sql` file in your PostgreSQL instance to create the necessary tables.

---

## gRPC Service

### Purpose:
The **gRPC service** is used to allow external systems to query data about animals and products. Specifically, the service can:
- Retrieve all products involving a particular animal.
- Retrieve all animals involved in a specific product.

### gRPC Service Definition:
The service is defined in a `.proto` file located in the project. The **Proto file** contains the service definition and message types, which can be compiled and used to generate the server and client code for gRPC communication.

---

## Stations Overview

### **Station 1 - Animal Registration (RESTful API)**
- Implements a **RESTful web service** to register animals.
- **Endpoints**:
  - `POST /animals`: Register a new animal.
  - `GET /animals/{id}`: Retrieve details for a specific animal.
  - `GET /animals/date/{date}`: Retrieve animals that arrived on a specific date.
  - `GET /animals/origin/{origin}`: Retrieve animals from a specific origin.

### **Station 2 - Cutting and Part Registration (RMI)**
- Processes animals into parts and stores them in trays.
- Uses **RMI** to communicate between the cutting station and the database.

### **Station 3 - Product Registration (RMI)**
- Creates and registers products made from parts.
- Uses **RMI** to communicate between the product station and the database.

---

## How to Run the System

### Steps to Start the Services:

1. **Start the Animal Registration Service (RESTful)**:
   - This service is responsible for registering animals that arrive at the slaughterhouse. It exposes the necessary RESTful endpoints for adding and retrieving animal data.

2. **Start the Cutting and Part Registration Service (RMI)**:
   - This service processes the animals into smaller parts and stores the parts in trays. It communicates via RMI for internal communication and registration.

3. **Start the Product Registration Service (RMI)**:
   - This service registers products that are packed with parts from the animals. It also communicates internally via RMI.

4. **Run the gRPC Service** (for external queries):
   - If you're using the gRPC service for external queries, start this service to expose the gRPC endpoints that can retrieve information about animals and products.

---

## Testing

- **JUnit Tests**:
   - Tests have been implemented for services like **animal registration** and **part registration**. The tests verify the functionality of the different services and ensure proper integration with the database.

- **BloomRPC/Postman**:
   - The **gRPC** and **RESTful** APIs have been tested using **BloomRPC** (for gRPC) and **Postman** (for RESTful). These tools ensure the APIs are working as expected.

---

## Conclusion

This system simulates the operation of a **slaughterhouse** and provides the necessary functionality for:
- Animal registration
- Part cutting
- Product packaging
- External product recall queries via gRPC

The stations in the system are designed to work independently, ensuring that the system can continue operating even during temporary outages.
