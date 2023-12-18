// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Book.proto

package com.xiaomi.demo.proto;

/**
 * Protobuf service {@code BookService}
 */
public  abstract class BookService
    implements com.google.protobuf.Service {
  protected BookService() {}

  public interface Interface {
    /**
     * <code>rpc getBooks(.BookRequest) returns (stream .BookResponse);</code>
     */
    public abstract void getBooks(
        com.google.protobuf.RpcController controller,
        com.xiaomi.demo.proto.BookRequest request,
        com.google.protobuf.RpcCallback<com.xiaomi.demo.proto.BookResponse> done);

  }

  public static com.google.protobuf.Service newReflectiveService(
      final Interface impl) {
    return new BookService() {
      @java.lang.Override
      public  void getBooks(
          com.google.protobuf.RpcController controller,
          com.xiaomi.demo.proto.BookRequest request,
          com.google.protobuf.RpcCallback<com.xiaomi.demo.proto.BookResponse> done) {
        impl.getBooks(controller, request, done);
      }

    };
  }

  public static com.google.protobuf.BlockingService
      newReflectiveBlockingService(final BlockingInterface impl) {
    return new com.google.protobuf.BlockingService() {
      public final com.google.protobuf.Descriptors.ServiceDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }

      public final com.google.protobuf.Message callBlockingMethod(
          com.google.protobuf.Descriptors.MethodDescriptor method,
          com.google.protobuf.RpcController controller,
          com.google.protobuf.Message request)
          throws com.google.protobuf.ServiceException {
        if (method.getService() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "Service.callBlockingMethod() given method descriptor for " +
            "wrong service type.");
        }
        switch(method.getIndex()) {
          case 0:
            return impl.getBooks(controller, (com.xiaomi.demo.proto.BookRequest)request);
          default:
            throw new java.lang.AssertionError("Can't get here.");
        }
      }

      public final com.google.protobuf.Message
          getRequestPrototype(
          com.google.protobuf.Descriptors.MethodDescriptor method) {
        if (method.getService() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "Service.getRequestPrototype() given method " +
            "descriptor for wrong service type.");
        }
        switch(method.getIndex()) {
          case 0:
            return com.xiaomi.demo.proto.BookRequest.getDefaultInstance();
          default:
            throw new java.lang.AssertionError("Can't get here.");
        }
      }

      public final com.google.protobuf.Message
          getResponsePrototype(
          com.google.protobuf.Descriptors.MethodDescriptor method) {
        if (method.getService() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "Service.getResponsePrototype() given method " +
            "descriptor for wrong service type.");
        }
        switch(method.getIndex()) {
          case 0:
            return com.xiaomi.demo.proto.BookResponse.getDefaultInstance();
          default:
            throw new java.lang.AssertionError("Can't get here.");
        }
      }

    };
  }

  /**
   * <code>rpc getBooks(.BookRequest) returns (stream .BookResponse);</code>
   */
  public abstract void getBooks(
      com.google.protobuf.RpcController controller,
      com.xiaomi.demo.proto.BookRequest request,
      com.google.protobuf.RpcCallback<com.xiaomi.demo.proto.BookResponse> done);

  public static final
      com.google.protobuf.Descriptors.ServiceDescriptor
      getDescriptor() {
    return com.xiaomi.demo.proto.BookOuterClass.getDescriptor().getServices().get(0);
  }
  public final com.google.protobuf.Descriptors.ServiceDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }

  public final void callMethod(
      com.google.protobuf.Descriptors.MethodDescriptor method,
      com.google.protobuf.RpcController controller,
      com.google.protobuf.Message request,
      com.google.protobuf.RpcCallback<
        com.google.protobuf.Message> done) {
    if (method.getService() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "Service.callMethod() given method descriptor for wrong " +
        "service type.");
    }
    switch(method.getIndex()) {
      case 0:
        this.getBooks(controller, (com.xiaomi.demo.proto.BookRequest)request,
          com.google.protobuf.RpcUtil.<com.xiaomi.demo.proto.BookResponse>specializeCallback(
            done));
        return;
      default:
        throw new java.lang.AssertionError("Can't get here.");
    }
  }

  public final com.google.protobuf.Message
      getRequestPrototype(
      com.google.protobuf.Descriptors.MethodDescriptor method) {
    if (method.getService() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "Service.getRequestPrototype() given method " +
        "descriptor for wrong service type.");
    }
    switch(method.getIndex()) {
      case 0:
        return com.xiaomi.demo.proto.BookRequest.getDefaultInstance();
      default:
        throw new java.lang.AssertionError("Can't get here.");
    }
  }

  public final com.google.protobuf.Message
      getResponsePrototype(
      com.google.protobuf.Descriptors.MethodDescriptor method) {
    if (method.getService() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "Service.getResponsePrototype() given method " +
        "descriptor for wrong service type.");
    }
    switch(method.getIndex()) {
      case 0:
        return com.xiaomi.demo.proto.BookResponse.getDefaultInstance();
      default:
        throw new java.lang.AssertionError("Can't get here.");
    }
  }

  public static Stub newStub(
      com.google.protobuf.RpcChannel channel) {
    return new Stub(channel);
  }

  public static final class Stub extends com.xiaomi.demo.proto.BookService implements Interface {
    private Stub(com.google.protobuf.RpcChannel channel) {
      this.channel = channel;
    }

    private final com.google.protobuf.RpcChannel channel;

    public com.google.protobuf.RpcChannel getChannel() {
      return channel;
    }

    public  void getBooks(
        com.google.protobuf.RpcController controller,
        com.xiaomi.demo.proto.BookRequest request,
        com.google.protobuf.RpcCallback<com.xiaomi.demo.proto.BookResponse> done) {
      channel.callMethod(
        getDescriptor().getMethods().get(0),
        controller,
        request,
        com.xiaomi.demo.proto.BookResponse.getDefaultInstance(),
        com.google.protobuf.RpcUtil.generalizeCallback(
          done,
          com.xiaomi.demo.proto.BookResponse.class,
          com.xiaomi.demo.proto.BookResponse.getDefaultInstance()));
    }
  }

  public static BlockingInterface newBlockingStub(
      com.google.protobuf.BlockingRpcChannel channel) {
    return new BlockingStub(channel);
  }

  public interface BlockingInterface {
    public com.xiaomi.demo.proto.BookResponse getBooks(
        com.google.protobuf.RpcController controller,
        com.xiaomi.demo.proto.BookRequest request)
        throws com.google.protobuf.ServiceException;
  }

  private static final class BlockingStub implements BlockingInterface {
    private BlockingStub(com.google.protobuf.BlockingRpcChannel channel) {
      this.channel = channel;
    }

    private final com.google.protobuf.BlockingRpcChannel channel;

    public com.xiaomi.demo.proto.BookResponse getBooks(
        com.google.protobuf.RpcController controller,
        com.xiaomi.demo.proto.BookRequest request)
        throws com.google.protobuf.ServiceException {
      return (com.xiaomi.demo.proto.BookResponse) channel.callBlockingMethod(
        getDescriptor().getMethods().get(0),
        controller,
        request,
        com.xiaomi.demo.proto.BookResponse.getDefaultInstance());
    }

  }

  // @@protoc_insertion_point(class_scope:BookService)
}

