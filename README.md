Slaughterhouse Management System - gRPC Overview
This system leverages gRPC for efficient, high-performance communication between three main stations in the animal processing workflow. gRPC allows:

High-speed Communication: Its lightweight protocol supports rapid data exchange between distributed stations.
Data Consistency: Strictly defined data types enforce data integrity across all stations.
Scalability: gRPC makes it simple to extend the system with additional stations or functionality.
Note: The gRPC integration is actively under development and is being tested on a separate branch, grpc-connection.

Implementation Summary
Each station is structured as an independent service with specific tasks in the workflow:

Station 1: Animal Registration
Station 2: Cutting and Parts Registration
Station 3: Product Registration
The .proto files serve as the blueprint for each service’s API, defining the methods and data structures used in inter-service communication. This includes:

Service Definition: For instance, Station 1 provides registerAnimal and listRegisteredAnimals methods; Station 2 handles addAnimalPart and getAnimalParts; Station 3 includes registerProduct and createOrder.
Automatic Stub Generation: The .proto files generate Java stubs, creating a consistent contract between services and enforcing strict data type requirements.
Seamless Communication: Each station acts as a gRPC server and client, allowing efficient data flow across the system.
With these features, gRPC enables scalable, reliable communication and smooth inter-service operations. The ongoing work on the grpc-connection branch focuses on refining these connections and ensuring optimal performance across all stations.
