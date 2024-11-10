package org.example.station3_productregistration.grpc;

import io.grpc.stub.StreamObserver;

import org.example.station3_productregistration.model.OrderProduct;
import org.example.station3_productregistration.model.Product;
import org.example.station3_productregistration.model.ProductTray;
import org.example.station3_productregistration.model.DistributionOrder;

import org.example.station3_productregistration.repository.ProductRepository;
import org.example.station3_productregistration.repository.ProductTrayRepository;
import org.example.station3_productregistration.repository.OrderProductRepository;
import org.example.station3_productregistration.repository.DistributionOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ProductRegistrationServiceImpl extends org.example.station3_productregistration.grpc.ProductRegistrationServiceGrpc.ProductRegistrationServiceImplBase {

    private final ProductRepository productRepository;

    private final ProductTrayRepository productTrayRepository;

    private final OrderProductRepository orderProductRepository;

    private final DistributionOrderRepository distributionOrderRepository;

    @Autowired
    public ProductRegistrationServiceImpl(ProductRepository productRepository, ProductTrayRepository productTrayRepository, OrderProductRepository orderProductRepository, DistributionOrderRepository distributionOrderRepository) {
        this.productRepository = productRepository;
        this.productTrayRepository = productTrayRepository;
        this.orderProductRepository = orderProductRepository;
        this.distributionOrderRepository = distributionOrderRepository;
    }

    @Override
    public void registerProduct(org.example.station3_productregistration.grpc.RegisterProductRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        Product product = new Product();
        product.setProductId(request.getProductId());
        product.setProductType(request.getProductType());

        productRepository.save(product);
        responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductById(org.example.station3_productregistration.grpc.GetProductByIdRequest request, StreamObserver<org.example.station3_productregistration.grpc.GetProductByIdResponse> responseObserver) {
        org.example.station3_productregistration.model.Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product != null) {
            org.example.station3_productregistration.grpc.GetProductByIdResponse response = org.example.station3_productregistration.grpc.GetProductByIdResponse.newBuilder()
                    .setProduct(toGrpcProduct(product)) // Use the conversion method here
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Product not found for ID: " + request.getProductId()));
        }
    }

    private org.example.station3_productregistration.grpc.Product toGrpcProduct(Product product) {
        return org.example.station3_productregistration.grpc.Product.newBuilder()
                .setProductId(product.getProductId())
                .setProductType(product.getProductType())
                .build();

    }


    @Override
    public void getAllProductTrays(com.google.protobuf.Empty request, StreamObserver<org.example.station3_productregistration.grpc.GetAllProductTraysResponse> responseObserver) {
        List<ProductTray> trays = productTrayRepository.findAll();
        List<org.example.station3_productregistration.grpc.ProductTrays> productTraysList = new ArrayList<>();

        for (ProductTray tray : trays) {
            productTraysList.add(org.example.station3_productregistration.grpc.ProductTrays.newBuilder()
                    .setProductId(tray.getProduct().getProductId())
                    .setTrayId(tray.getTray().getTrayId()) // Make sure to have trayId getter
                    .build());
        }

        org.example.station3_productregistration.grpc.GetAllProductTraysResponse response = org.example.station3_productregistration.grpc.GetAllProductTraysResponse.newBuilder()
                .addAllTrays(productTraysList)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllProductTypes(com.google.protobuf.Empty request, StreamObserver<org.example.station3_productregistration.grpc.GetAllProductTypesResponse> responseObserver) {
        List<String> productTypes = productRepository.findAll().stream()
                .map(Product::getProductType)
                .distinct()
                .collect(Collectors.toList());

        org.example.station3_productregistration.grpc.GetAllProductTypesResponse response = org.example.station3_productregistration.grpc.GetAllProductTypesResponse.newBuilder()
                .addAllProductTypes(productTypes)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createOrder(org.example.station3_productregistration.grpc.CreateOrderRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderId(request.getOrderId());
        orderProduct.setProductId(request.getProductId());

        orderProductRepository.save(orderProduct);
        responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getOrderById(org.example.station3_productregistration.grpc.GetOrderByIdRequest request, StreamObserver<org.example.station3_productregistration.grpc.GetOrderByIdResponse> responseObserver) {
        OrderProduct orderProduct = orderProductRepository.findById(request.getOrderId()).orElse(null);
        if (orderProduct != null) {
            org.example.station3_productregistration.grpc.OrderProducts orderProducts = org.example.station3_productregistration.grpc.OrderProducts.newBuilder()
                    .setOrderId(orderProduct.getOrderId())
                    .setProductId(orderProduct.getProductId())
                    .build();
            org.example.station3_productregistration.grpc.GetOrderByIdResponse response = org.example.station3_productregistration.grpc.GetOrderByIdResponse.newBuilder()
                    .setOrderProducts(orderProducts)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Order not found for ID: " + request.getOrderId()));
        }
    }

    @Override
    public void createDistributionOrder(org.example.station3_productregistration.grpc.CreateDistributionOrderRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        DistributionOrder distributionOrder = new DistributionOrder();
        distributionOrder.setOrderId(request.getOrderId());
        distributionOrder.setCustomerDetails(request.getCustomerDetails());
        distributionOrder.setShippingDate(request.getShippingDate());

        distributionOrderRepository.save(distributionOrder);
        responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getDistributionOrderById(org.example.station3_productregistration.grpc.GetDistributionOrderByIdRequest request, StreamObserver<org.example.station3_productregistration.grpc.GetDistributionOrderByIdResponse> responseObserver) {
        org.example.station3_productregistration.model.DistributionOrder distributionOrder = distributionOrderRepository.findById(request.getOrderId()).orElse(null);
        if (distributionOrder != null) {
            org.example.station3_productregistration.grpc.GetDistributionOrderByIdResponse response = org.example.station3_productregistration.grpc.GetDistributionOrderByIdResponse.newBuilder()
                    .setDistributionOrder(toGrpcDistributionOrder(distributionOrder)) // Use the conversion method here
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Distribution order not found for ID: " + request.getOrderId()));
        }
    }

    private org.example.station3_productregistration.grpc.DistributionOrder toGrpcDistributionOrder(DistributionOrder distributionOrder) {
        return org.example.station3_productregistration.grpc.DistributionOrder.newBuilder()
                .setOrderId(distributionOrder.getOrderId())
                .setCustomerDetails(distributionOrder.getCustomerDetails())
                .setShippingDate(distributionOrder.getShippingDate())
                .build();

    }

}
