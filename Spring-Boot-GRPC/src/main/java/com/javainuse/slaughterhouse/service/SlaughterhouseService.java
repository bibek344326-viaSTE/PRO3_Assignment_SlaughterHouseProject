package com.javainuse.slaughterhouse.service;

import com.javainuse.employee.ProductRequest;
import com.javainuse.employee.AnimalResponse;
import com.javainuse.employee.SlaughterhouseServiceGrpc;
import com.javainuse.employee.AnimalRequest;
import com.javainuse.employee.ProductResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class SlaughterhouseService extends SlaughterhouseServiceGrpc.SlaughterhouseServiceImplBase {

	private final DataSource dataSource;

	@Autowired
	public SlaughterhouseService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	@Override
	public void getAnimalsForProduct(ProductRequest request, StreamObserver<AnimalResponse> responseObserver) {
		System.out.println("Reached getAnimalsForProduct method with productId: " + request.getProductId());

		String productId = request.getProductId();
		List<String> animalIds = new ArrayList<>();

		String query = "SELECT DISTINCT a.animal_id FROM animals a JOIN animal_parts ap ON a.animal_id = ap.animal_id JOIN tray_parts tp ON ap.part_id = tp.part_id JOIN product_trays pt ON tp.tray_id = pt.tray_id WHERE pt.product_id = ?; ";

		try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, productId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				animalIds.add(rs.getString("animal_id"));
			}
		} catch (Exception e) {
			responseObserver.onError(e);
			return;
		}

		// Build the response
		AnimalResponse response = AnimalResponse.newBuilder()
				.addAllAnimalIds(animalIds)
				.build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void getProductsForAnimal(AnimalRequest request, StreamObserver<ProductResponse> responseObserver) {
		String animalId = request.getAnimalId();
		List<String> productIds = new ArrayList<>();

		String query = "SELECT DISTINCT p.product_id FROM products p JOIN product_trays pt ON p.product_id = pt.product_id JOIN tray_parts tp ON pt.tray_id = tp.tray_id JOIN animal_parts ap ON tp.part_id = ap.part_id WHERE ap.animal_id = ?; ";

		try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, animalId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				productIds.add(rs.getString("product_id"));
			}
		} catch (Exception e) {
			responseObserver.onError(e);
			return;
		}

		// Build the response
		ProductResponse response = ProductResponse.newBuilder()
				.addAllProductIds(productIds)
				.build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
