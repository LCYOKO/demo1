package com.xiaomi.demo.rpc.grpc;

import io.grpc.*;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuchiyun
 * @Date: 2024/10/24
 */
public class MyGrpcClientInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        CallOptions myCallOptions = callOptions
                .withDeadlineAfter(500, TimeUnit.MILLISECONDS)
                .withCallCredentials(new CallCredentials() {
                    @Override
                    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
                        Metadata metadata = new Metadata();
                        metadata.put(Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER), "123");
                        applier.apply(metadata);
                    }

                    @Override
                    public void thisUsesUnstableApi() {}
                });
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, myCallOptions)) {
            @Override
            public void sendMessage(ReqT message) {
                System.out.println("request method: " + method.getFullMethodName());
                System.out.println("request param：" + message.toString());
                super.sendMessage(message);
            }
        };
    }
}
