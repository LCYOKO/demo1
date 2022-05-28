package com.xiaomi.demo.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 0.15.0)",
    comments = "Source: Book.proto")
public class BookServiceGrpc {

  private BookServiceGrpc() {}

  public static final String SERVICE_NAME = "BookService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<BookRequest,
      BookResponse> METHOD_GET_BOOKS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "BookService", "getBooks"),
          io.grpc.protobuf.ProtoUtils.marshaller(BookRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(BookResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BookServiceStub newStub(io.grpc.Channel channel) {
    return new BookServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BookServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new BookServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static BookServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new BookServiceFutureStub(channel);
  }

  /**
   */
  @Deprecated public static interface BookService {

    /**
     */
    public void getBooks(BookRequest request,
                         io.grpc.stub.StreamObserver<BookResponse> responseObserver);
  }

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1469")
  public static abstract class BookServiceImplBase implements BookService, io.grpc.BindableService {

    @Override
    public void getBooks(BookRequest request,
                         io.grpc.stub.StreamObserver<BookResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BOOKS, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return BookServiceGrpc.bindService(this);
    }
  }

  /**
   */
  @Deprecated public static interface BookServiceBlockingClient {

    /**
     */
    public java.util.Iterator<BookResponse> getBooks(
            BookRequest request);
  }

  /**
   */
  @Deprecated public static interface BookServiceFutureClient {
  }

  public static class BookServiceStub extends io.grpc.stub.AbstractStub<BookServiceStub>
      implements BookService {
    private BookServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected BookServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookServiceStub(channel, callOptions);
    }

    @Override
    public void getBooks(BookRequest request,
                         io.grpc.stub.StreamObserver<BookResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_BOOKS, getCallOptions()), request, responseObserver);
    }
  }

  public static class BookServiceBlockingStub extends io.grpc.stub.AbstractStub<BookServiceBlockingStub>
      implements BookServiceBlockingClient {
    private BookServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected BookServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookServiceBlockingStub(channel, callOptions);
    }

    @Override
    public java.util.Iterator<BookResponse> getBooks(
        BookRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_BOOKS, getCallOptions(), request);
    }
  }

  public static class BookServiceFutureStub extends io.grpc.stub.AbstractStub<BookServiceFutureStub>
      implements BookServiceFutureClient {
    private BookServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected BookServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookServiceFutureStub(channel, callOptions);
    }
  }

  @Deprecated public static abstract class AbstractBookService extends BookServiceImplBase {}

  private static final int METHODID_GET_BOOKS = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BookService serviceImpl;
    private final int methodId;

    public MethodHandlers(BookService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_BOOKS:
          serviceImpl.getBooks((BookRequest) request,
              (io.grpc.stub.StreamObserver<BookResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_GET_BOOKS);
  }

  @Deprecated public static io.grpc.ServerServiceDefinition bindService(
      final BookService serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          METHOD_GET_BOOKS,
          asyncServerStreamingCall(
            new MethodHandlers<
              BookRequest,
              BookResponse>(
                serviceImpl, METHODID_GET_BOOKS)))
        .build();
  }
}
