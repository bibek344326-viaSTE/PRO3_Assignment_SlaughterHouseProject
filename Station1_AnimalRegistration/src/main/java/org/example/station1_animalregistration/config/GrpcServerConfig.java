package org.example.station1_animalregistration.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.station1_animalregistration.grpc.AnimalRegistrationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
public class GrpcServerConfig {

    private Server server;

    // Inject AnimalRegistrationServiceImpl as a Spring Bean
    @Bean
    public Server grpcServer(AnimalRegistrationServiceImpl animalRegistrationServiceImpl) throws IOException, SQLException, ClassNotFoundException {
        int port = 9090; // Define your port
        server = ServerBuilder.forPort(port)
                .addService(animalRegistrationServiceImpl) // Register the injected service
                .build()
                .start();

        // Log server started
        System.out.println("gRPC server started on port: " + port);

        return server;
    }

    @PostConstruct
    public void start() {
        // Keep the application context running
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        System.out.println("gRPC server is running.");
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination();
            System.out.println("gRPC server stopped.");
        }
    }
}
