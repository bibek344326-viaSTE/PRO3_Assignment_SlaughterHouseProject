package org.example.productanimalinfoservice.grpc;

import java.util.*;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoServiceImpl extends ProductInfoServiceGrpc.ProductInfoServiceImplBase {

    // Mock database for products and animals associations
    private final Map<String, List<String>> productToAnimalsMap = new HashMap<>();
    private final Map<String, List<String>> animalToProductsMap = new HashMap<>();

    public ProductInfoServiceImpl() {
        // Initialize mock data
        productToAnimalsMap.put("product1", Arrays.asList("animalA", "animalB"));
        productToAnimalsMap.put("product2", Arrays.asList("animalC"));
        animalToProductsMap.put("animalA", Arrays.asList("product1"));
        animalToProductsMap.put("animalB", Arrays.asList("product1"));
        animalToProductsMap.put("animalC", Arrays.asList("product2"));
    }

    @Override
    public void GetAnimalsByProduct(ProductRequest request, StreamObserver<AnimalListResponse> responseObserver) {
        List<String> animals = productToAnimalsMap.getOrDefault(request.getProductId(), Collections.emptyList());
        AnimalListResponse response = AnimalListResponse.newBuilder().addAllRegistrationNumbers(animals).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void GetProductsByAnimal(AnimalRequest request, StreamObserver<ProductListResponse> responseObserver) {
        List<String> products = animalToProductsMap.getOrDefault(request.getRegistrationNumber(), Collections.emptyList());
        ProductListResponse response = ProductListResponse.newBuilder().addAllProductIds(products).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}