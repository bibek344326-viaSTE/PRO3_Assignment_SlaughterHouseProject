package com.javainuse.employee;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.22.1)",
    comments = "Source: slaughterhouse-service.proto")
public final class SlaughterhouseServiceGrpc {

  private SlaughterhouseServiceGrpc() {}

  public static final String SERVICE_NAME = "SlaughterhouseService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.javainuse.employee.ProductRequest,
      com.javainuse.employee.AnimalResponse> getGetAnimalsForProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAnimalsForProduct",
      requestType = com.javainuse.employee.ProductRequest.class,
      responseType = com.javainuse.employee.AnimalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.javainuse.employee.ProductRequest,
      com.javainuse.employee.AnimalResponse> getGetAnimalsForProductMethod() {
    io.grpc.MethodDescriptor<com.javainuse.employee.ProductRequest, com.javainuse.employee.AnimalResponse> getGetAnimalsForProductMethod;
    if ((getGetAnimalsForProductMethod = SlaughterhouseServiceGrpc.getGetAnimalsForProductMethod) == null) {
      synchronized (SlaughterhouseServiceGrpc.class) {
        if ((getGetAnimalsForProductMethod = SlaughterhouseServiceGrpc.getGetAnimalsForProductMethod) == null) {
          SlaughterhouseServiceGrpc.getGetAnimalsForProductMethod = getGetAnimalsForProductMethod = 
              io.grpc.MethodDescriptor.<com.javainuse.employee.ProductRequest, com.javainuse.employee.AnimalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "SlaughterhouseService", "GetAnimalsForProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.javainuse.employee.ProductRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.javainuse.employee.AnimalResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SlaughterhouseServiceMethodDescriptorSupplier("GetAnimalsForProduct"))
                  .build();
          }
        }
     }
     return getGetAnimalsForProductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.javainuse.employee.AnimalRequest,
      com.javainuse.employee.ProductResponse> getGetProductsForAnimalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsForAnimal",
      requestType = com.javainuse.employee.AnimalRequest.class,
      responseType = com.javainuse.employee.ProductResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.javainuse.employee.AnimalRequest,
      com.javainuse.employee.ProductResponse> getGetProductsForAnimalMethod() {
    io.grpc.MethodDescriptor<com.javainuse.employee.AnimalRequest, com.javainuse.employee.ProductResponse> getGetProductsForAnimalMethod;
    if ((getGetProductsForAnimalMethod = SlaughterhouseServiceGrpc.getGetProductsForAnimalMethod) == null) {
      synchronized (SlaughterhouseServiceGrpc.class) {
        if ((getGetProductsForAnimalMethod = SlaughterhouseServiceGrpc.getGetProductsForAnimalMethod) == null) {
          SlaughterhouseServiceGrpc.getGetProductsForAnimalMethod = getGetProductsForAnimalMethod = 
              io.grpc.MethodDescriptor.<com.javainuse.employee.AnimalRequest, com.javainuse.employee.ProductResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "SlaughterhouseService", "GetProductsForAnimal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.javainuse.employee.AnimalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.javainuse.employee.ProductResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SlaughterhouseServiceMethodDescriptorSupplier("GetProductsForAnimal"))
                  .build();
          }
        }
     }
     return getGetProductsForAnimalMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SlaughterhouseServiceStub newStub(io.grpc.Channel channel) {
    return new SlaughterhouseServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SlaughterhouseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SlaughterhouseServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SlaughterhouseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SlaughterhouseServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SlaughterhouseServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAnimalsForProduct(com.javainuse.employee.ProductRequest request,
        io.grpc.stub.StreamObserver<com.javainuse.employee.AnimalResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAnimalsForProductMethod(), responseObserver);
    }

    /**
     */
    public void getProductsForAnimal(com.javainuse.employee.AnimalRequest request,
        io.grpc.stub.StreamObserver<com.javainuse.employee.ProductResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetProductsForAnimalMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAnimalsForProductMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.javainuse.employee.ProductRequest,
                com.javainuse.employee.AnimalResponse>(
                  this, METHODID_GET_ANIMALS_FOR_PRODUCT)))
          .addMethod(
            getGetProductsForAnimalMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.javainuse.employee.AnimalRequest,
                com.javainuse.employee.ProductResponse>(
                  this, METHODID_GET_PRODUCTS_FOR_ANIMAL)))
          .build();
    }
  }

  /**
   */
  public static final class SlaughterhouseServiceStub extends io.grpc.stub.AbstractStub<SlaughterhouseServiceStub> {
    private SlaughterhouseServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SlaughterhouseServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SlaughterhouseServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SlaughterhouseServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAnimalsForProduct(com.javainuse.employee.ProductRequest request,
        io.grpc.stub.StreamObserver<com.javainuse.employee.AnimalResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAnimalsForProductMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProductsForAnimal(com.javainuse.employee.AnimalRequest request,
        io.grpc.stub.StreamObserver<com.javainuse.employee.ProductResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetProductsForAnimalMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SlaughterhouseServiceBlockingStub extends io.grpc.stub.AbstractStub<SlaughterhouseServiceBlockingStub> {
    private SlaughterhouseServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SlaughterhouseServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SlaughterhouseServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SlaughterhouseServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.javainuse.employee.AnimalResponse getAnimalsForProduct(com.javainuse.employee.ProductRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAnimalsForProductMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.javainuse.employee.ProductResponse getProductsForAnimal(com.javainuse.employee.AnimalRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetProductsForAnimalMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SlaughterhouseServiceFutureStub extends io.grpc.stub.AbstractStub<SlaughterhouseServiceFutureStub> {
    private SlaughterhouseServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SlaughterhouseServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SlaughterhouseServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SlaughterhouseServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.javainuse.employee.AnimalResponse> getAnimalsForProduct(
        com.javainuse.employee.ProductRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAnimalsForProductMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.javainuse.employee.ProductResponse> getProductsForAnimal(
        com.javainuse.employee.AnimalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetProductsForAnimalMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ANIMALS_FOR_PRODUCT = 0;
  private static final int METHODID_GET_PRODUCTS_FOR_ANIMAL = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SlaughterhouseServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SlaughterhouseServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ANIMALS_FOR_PRODUCT:
          serviceImpl.getAnimalsForProduct((com.javainuse.employee.ProductRequest) request,
              (io.grpc.stub.StreamObserver<com.javainuse.employee.AnimalResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_FOR_ANIMAL:
          serviceImpl.getProductsForAnimal((com.javainuse.employee.AnimalRequest) request,
              (io.grpc.stub.StreamObserver<com.javainuse.employee.ProductResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SlaughterhouseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SlaughterhouseServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.javainuse.employee.SlaughterhouseServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SlaughterhouseService");
    }
  }

  private static final class SlaughterhouseServiceFileDescriptorSupplier
      extends SlaughterhouseServiceBaseDescriptorSupplier {
    SlaughterhouseServiceFileDescriptorSupplier() {}
  }

  private static final class SlaughterhouseServiceMethodDescriptorSupplier
      extends SlaughterhouseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SlaughterhouseServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SlaughterhouseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SlaughterhouseServiceFileDescriptorSupplier())
              .addMethod(getGetAnimalsForProductMethod())
              .addMethod(getGetProductsForAnimalMethod())
              .build();
        }
      }
    }
    return result;
  }
}
