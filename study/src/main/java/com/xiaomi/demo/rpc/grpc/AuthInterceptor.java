package com.xiaomi.demo.rpc.grpc;

import io.grpc.*;

/**
 * @Author: liuchiyun
 * @Date: 2024/10/23
 */
public class AuthInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> next) {
        Status status;
        String id = metadata.get(Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER));
        if (id == null) {
            status = Status.UNAUTHENTICATED.withDescription("miss authentication token");
        } else if (!id.equals("123")) {
            status = Status.UNAUTHENTICATED.withDescription("invalid authentication token");
        } else {
            return next.startCall(serverCall, metadata);
        }
        serverCall.close(status, new Metadata());
        return new ServerCall.Listener<ReqT>() {
        };
    }
}
