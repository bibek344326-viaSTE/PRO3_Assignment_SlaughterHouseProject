package org.example.productanimalinfoservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ProductAnimalInfoClient {

    private final ProductInfoServiceGrpc.ProductInfoServiceBlockingStub blockingStub;

    public ProductAnimalInfoClient(ManagedChannel channel) {
        blockingStub = ProductInfoServiceGrpc.newBlockingStub(channel);
    }

    public void getAnimalsByProduct(String productId) {
        ProductRequest request = ProductRequest.newBuilder().setProductId(productId).build();
        AnimalListResponse response = blockingStub.getAnimalsByProduct(request);
        System.out.println("Animals for product " + productId + ": " + response.getRegistrationNumbersList());
    }

    public void getProductsByAnimal(String registrationNumber) {
        AnimalRequest request = AnimalRequest.newBuilder().setRegistrationNumber(registrationNumber).build();
        ProductListResponse response = blockingStub.getProductsByAnimal(request);
        System.out.println("Products for animal " + registrationNumber + ": " + response.getProductIdsList());
    }

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ProductAnimalInfoClient client = new ProductAnimalInfoClient(channel);

        client.getAnimalsByProduct("product1");
        client.getProductsByAnimal("animalA");

        channel.shutdown();
    }
}
