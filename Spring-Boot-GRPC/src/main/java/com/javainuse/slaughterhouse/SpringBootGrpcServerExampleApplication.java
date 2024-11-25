package com.javainuse.slaughterhouse;

import com.javainuse.slaughterhouse.service.SlaughterhouseService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class SpringBootGrpcServerExampleApplication {

	@Autowired
	private SlaughterhouseService slaughterhouseService;

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(SpringBootGrpcServerExampleApplication.class, args);
	}

	@PostConstruct
	public void startGrpcServer() throws IOException, InterruptedException {
		// Start the gRPC server with Spring-managed SlaughterhouseService
		Server server = ServerBuilder.forPort(8090)
				.addService(slaughterhouseService) // Let Spring inject dependencies
				.build();

		System.out.println("Starting server on port 8090...");
		server.start();
		server.awaitTermination();
	}
}
