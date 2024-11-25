package slaughterhouse;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;


import java.io.IOException;

@SpringBootApplication
public class SpringBootGrpcServerApplication
{

	public static void main(String[] args)
      throws IOException, InterruptedException
  {
		SpringApplication.run(SpringBootGrpcServerApplication.class, args);
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres?currentSchema=slaughterhousedb",
					"postgres", "Sneha123"
			);
		} catch (Exception e) {
			System.err.println("Failed to connect to database: " + e.getMessage());
			System.exit(1);
		}

		// Start the gRPC server
		Server server = ServerBuilder.forPort(8090)
				.addService(new SlaughterhouseServiceImpl(connection))
				.build();

		System.out.println("Starting server on port 8080...");
		server.start();
		server.awaitTermination();

	}

}
