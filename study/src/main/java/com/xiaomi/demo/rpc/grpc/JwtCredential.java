package com.xiaomi.demo.rpc.grpc;

import com.aliyuncs.auth.AuthConstant;
import com.google.rpc.RequestInfo;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

import java.util.concurrent.Executor;

/**
 * @Author: liuchiyun
 * @Date: 2024/10/23
 */
public class JwtCredential extends CallCredentials {
    private String subject;

    public JwtCredential(String subject) {
        this.subject = subject;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, CallCredentials.MetadataApplier metadataApplier) {
        executor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                headers.put(Metadata.Key.of(AuthConstant.AUTH_HEADER, Metadata.ASCII_STRING_MARSHALLER),
                        String.format("%s %s", AuthConstant.AUTH_TOKEN_TYPE, subject));
                metadataApplier.apply(headers);
            } catch (Throwable e) {
                metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {

    }
}
