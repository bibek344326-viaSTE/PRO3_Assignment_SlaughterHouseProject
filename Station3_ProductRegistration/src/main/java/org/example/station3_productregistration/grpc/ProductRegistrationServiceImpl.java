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
public class ProductRegistrationServiceImpl extends ProductRegistrationServiceGrpc.ProductRegistrationServiceImplBase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTrayRepository productTrayRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private DistributionOrderRepository distributionOrderRepository;

    @Override
    public void registerProduct(RegisterProductRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        Product product = new Product();
        product.setProductId(request.getProductId());
        product.setProductType(request.getProductType());

        productRepository.save(product);
        responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductById(GetProductByIdRequest request, StreamObserver<GetProductByIdResponse> responseObserver) {
        org.example.station3_productregistration.model.Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product != null) {
            GetProductByIdResponse response = GetProductByIdResponse.newBuilder()
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
    public void getAllProductTrays(com.google.protobuf.Empty request, StreamObserver<GetAllProductTraysResponse> responseObserver) {
        List<ProductTray> trays = productTrayRepository.findAll();
        List<ProductTrays> productTraysList = new ArrayList<>();

        for (ProductTray tray : trays) {
            productTraysList.add(ProductTrays.newBuilder()
                    .setProductId(tray.getProduct().getProductId())
                    .setTrayId(tray.getTray().getTrayId()) // Make sure to have trayId getter
                    .build());
        }

        GetAllProductTraysResponse response = GetAllProductTraysResponse.newBuilder()
                .addAllTrays(productTraysList)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllProductTypes(com.google.protobuf.Empty request, StreamObserver<GetAllProductTypesResponse> responseObserver) {
        List<String> productTypes = productRepository.findAll().stream()
                .map(Product::getProductType)
                .distinct()
                .collect(Collectors.toList());

        GetAllProductTypesResponse response = GetAllProductTypesResponse.newBuilder()
                .addAllProductTypes(productTypes)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderId(request.getOrderId());
        orderProduct.setProductId(request.getProductId());

        orderProductRepository.save(orderProduct);
        responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getOrderById(GetOrderByIdRequest request, StreamObserver<GetOrderByIdResponse> responseObserver) {
        OrderProduct orderProduct = orderProductRepository.findById(request.getOrderId()).orElse(null);
        if (orderProduct != null) {
            OrderProducts orderProducts = OrderProducts.newBuilder()
                    .setOrderId(orderProduct.getOrderId())
                    .setProductId(orderProduct.getProductId())
                    .build();
            GetOrderByIdResponse response = GetOrderByIdResponse.newBuilder()
                    .setOrderProducts(orderProducts)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Order not found for ID: " + request.getOrderId()));
        }
    }

    @Override
    public void createDistributionOrder(CreateDistributionOrderRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        DistributionOrder distributionOrder = new DistributionOrder();
        distributionOrder.setOrderId(request.getOrderId());
        distributionOrder.setCustomerDetails(request.getCustomerDetails());
        distributionOrder.setShippingDate(request.getShippingDate());

        distributionOrderRepository.save(distributionOrder);
        responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getDistributionOrderById(GetDistributionOrderByIdRequest request, StreamObserver<GetDistributionOrderByIdResponse> responseObserver) {
        org.example.station3_productregistration.model.DistributionOrder distributionOrder = distributionOrderRepository.findById(request.getOrderId()).orElse(null);
        if (distributionOrder != null) {
            GetDistributionOrderByIdResponse response = GetDistributionOrderByIdResponse.newBuilder()
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
