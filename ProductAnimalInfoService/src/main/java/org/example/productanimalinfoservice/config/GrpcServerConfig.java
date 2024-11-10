package org.example.productanimalinfoservice.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.productanimalinfoservice.grpc.ProductInfoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    private Server server;

    @Bean
    public Server grpcServer(ProductInfoServiceImpl productInfoService) throws IOException {
        int port = 9091; // Define your port
        server = ServerBuilder.forPort(port)
                .addService(productInfoService)
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
