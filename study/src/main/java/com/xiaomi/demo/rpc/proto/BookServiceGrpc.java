package com.xiaomi.demo.rpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: Book.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BookServiceGrpc {

  private BookServiceGrpc() {}

  public static final String SERVICE_NAME = "BookService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest,
      com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks1Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBooks1",
      requestType = com.xiaomi.demo.rpc.proto.BookRequest.class,
      responseType = com.xiaomi.demo.rpc.proto.BookResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest,
      com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks1Method() {
    io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest, com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks1Method;
    if ((getGetBooks1Method = BookServiceGrpc.getGetBooks1Method) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getGetBooks1Method = BookServiceGrpc.getGetBooks1Method) == null) {
          BookServiceGrpc.getGetBooks1Method = getGetBooks1Method =
              io.grpc.MethodDescriptor.<com.xiaomi.demo.rpc.proto.BookRequest, com.xiaomi.demo.rpc.proto.BookResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBooks1"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xiaomi.demo.rpc.proto.BookRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xiaomi.demo.rpc.proto.BookResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("getBooks1"))
              .build();
        }
      }
    }
    return getGetBooks1Method;
  }

  private static volatile io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest,
      com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks2Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBooks2",
      requestType = com.xiaomi.demo.rpc.proto.BookRequest.class,
      responseType = com.xiaomi.demo.rpc.proto.BookResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest,
      com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks2Method() {
    io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest, com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks2Method;
    if ((getGetBooks2Method = BookServiceGrpc.getGetBooks2Method) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getGetBooks2Method = BookServiceGrpc.getGetBooks2Method) == null) {
          BookServiceGrpc.getGetBooks2Method = getGetBooks2Method =
              io.grpc.MethodDescriptor.<com.xiaomi.demo.rpc.proto.BookRequest, com.xiaomi.demo.rpc.proto.BookResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBooks2"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xiaomi.demo.rpc.proto.BookRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xiaomi.demo.rpc.proto.BookResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("getBooks2"))
              .build();
        }
      }
    }
    return getGetBooks2Method;
  }

  private static volatile io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest,
      com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks3Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBooks3",
      requestType = com.xiaomi.demo.rpc.proto.BookRequest.class,
      responseType = com.xiaomi.demo.rpc.proto.BookResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest,
      com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks3Method() {
    io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest, com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks3Method;
    if ((getGetBooks3Method = BookServiceGrpc.getGetBooks3Method) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getGetBooks3Method = BookServiceGrpc.getGetBooks3Method) == null) {
          BookServiceGrpc.getGetBooks3Method = getGetBooks3Method =
              io.grpc.MethodDescriptor.<com.xiaomi.demo.rpc.proto.BookRequest, com.xiaomi.demo.rpc.proto.BookResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBooks3"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xiaomi.demo.rpc.proto.BookRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xiaomi.demo.rpc.proto.BookResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("getBooks3"))
              .build();
        }
      }
    }
    return getGetBooks3Method;
  }

  private static volatile io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest,
      com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks4Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBooks4",
      requestType = com.xiaomi.demo.rpc.proto.BookRequest.class,
      responseType = com.xiaomi.demo.rpc.proto.BookResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest,
      com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks4Method() {
    io.grpc.MethodDescriptor<com.xiaomi.demo.rpc.proto.BookRequest, com.xiaomi.demo.rpc.proto.BookResponse> getGetBooks4Method;
    if ((getGetBooks4Method = BookServiceGrpc.getGetBooks4Method) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getGetBooks4Method = BookServiceGrpc.getGetBooks4Method) == null) {
          BookServiceGrpc.getGetBooks4Method = getGetBooks4Method =
              io.grpc.MethodDescriptor.<com.xiaomi.demo.rpc.proto.BookRequest, com.xiaomi.demo.rpc.proto.BookResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBooks4"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xiaomi.demo.rpc.proto.BookRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xiaomi.demo.rpc.proto.BookResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("getBooks4"))
              .build();
        }
      }
    }
    return getGetBooks4Method;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BookServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BookServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BookServiceStub>() {
        @java.lang.Override
        public BookServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BookServiceStub(channel, callOptions);
        }
      };
    return BookServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BookServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BookServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BookServiceBlockingStub>() {
        @java.lang.Override
        public BookServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BookServiceBlockingStub(channel, callOptions);
        }
      };
    return BookServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BookServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BookServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BookServiceFutureStub>() {
        @java.lang.Override
        public BookServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BookServiceFutureStub(channel, callOptions);
        }
      };
    return BookServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class BookServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getBooks1(com.xiaomi.demo.rpc.proto.BookRequest request,
                          io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBooks1Method(), responseObserver);
    }

    /**
     */
    public void getBooks2(com.xiaomi.demo.rpc.proto.BookRequest request,
                          io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBooks2Method(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookRequest> getBooks3(
        io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getGetBooks3Method(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookRequest> getBooks4(
        io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getGetBooks4Method(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetBooks1Method(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.xiaomi.demo.rpc.proto.BookRequest,
                com.xiaomi.demo.rpc.proto.BookResponse>(
                  this, METHODID_GET_BOOKS1)))
          .addMethod(
            getGetBooks2Method(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                com.xiaomi.demo.rpc.proto.BookRequest,
                com.xiaomi.demo.rpc.proto.BookResponse>(
                  this, METHODID_GET_BOOKS2)))
          .addMethod(
            getGetBooks3Method(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                com.xiaomi.demo.rpc.proto.BookRequest,
                com.xiaomi.demo.rpc.proto.BookResponse>(
                  this, METHODID_GET_BOOKS3)))
          .addMethod(
            getGetBooks4Method(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                com.xiaomi.demo.rpc.proto.BookRequest,
                com.xiaomi.demo.rpc.proto.BookResponse>(
                  this, METHODID_GET_BOOKS4)))
          .build();
    }
  }

  /**
   */
  public static final class BookServiceStub extends io.grpc.stub.AbstractAsyncStub<BookServiceStub> {
    private BookServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BookServiceStub(channel, callOptions);
    }

    /**
     */
    public void getBooks1(com.xiaomi.demo.rpc.proto.BookRequest request,
                          io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBooks1Method(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBooks2(com.xiaomi.demo.rpc.proto.BookRequest request,
                          io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetBooks2Method(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookRequest> getBooks3(
        io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getGetBooks3Method(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookRequest> getBooks4(
        io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getGetBooks4Method(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class BookServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<BookServiceBlockingStub> {
    private BookServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BookServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.xiaomi.demo.rpc.proto.BookResponse getBooks1(com.xiaomi.demo.rpc.proto.BookRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBooks1Method(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.xiaomi.demo.rpc.proto.BookResponse> getBooks2(
        com.xiaomi.demo.rpc.proto.BookRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetBooks2Method(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BookServiceFutureStub extends io.grpc.stub.AbstractFutureStub<BookServiceFutureStub> {
    private BookServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BookServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.xiaomi.demo.rpc.proto.BookResponse> getBooks1(
        com.xiaomi.demo.rpc.proto.BookRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBooks1Method(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_BOOKS1 = 0;
  private static final int METHODID_GET_BOOKS2 = 1;
  private static final int METHODID_GET_BOOKS3 = 2;
  private static final int METHODID_GET_BOOKS4 = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BookServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BookServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_BOOKS1:
          serviceImpl.getBooks1((com.xiaomi.demo.rpc.proto.BookRequest) request,
              (io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse>) responseObserver);
          break;
        case METHODID_GET_BOOKS2:
          serviceImpl.getBooks2((com.xiaomi.demo.rpc.proto.BookRequest) request,
              (io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse>) responseObserver);
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
        case METHODID_GET_BOOKS3:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getBooks3(
              (io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse>) responseObserver);
        case METHODID_GET_BOOKS4:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getBooks4(
              (io.grpc.stub.StreamObserver<com.xiaomi.demo.rpc.proto.BookResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class BookServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BookServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.xiaomi.demo.rpc.proto.BookOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BookService");
    }
  }

  private static final class BookServiceFileDescriptorSupplier
      extends BookServiceBaseDescriptorSupplier {
    BookServiceFileDescriptorSupplier() {}
  }

  private static final class BookServiceMethodDescriptorSupplier
      extends BookServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BookServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (BookServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BookServiceFileDescriptorSupplier())
              .addMethod(getGetBooks1Method())
              .addMethod(getGetBooks2Method())
              .addMethod(getGetBooks3Method())
              .addMethod(getGetBooks4Method())
              .build();
        }
      }
    }
    return result;
  }
}
