package com.xiaomi.demo.rpc.grpc;

import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/19
 */
@Slf4j
public class GrpcServer {

    public GrpcServer() {
    }

    public void startServer() {
        try {
            io.grpc.Server server = ServerBuilder.forPort(6333)
                    .addService(ServerInterceptors.intercept(new BookServiceImpl(), new AuthInterceptor()))
                    .build();
            server.start();
            server.awaitTermination();
        } catch (Exception ex) {
            log.error("start server failed.", ex);
        }
    }

    public static void main(String[] args) {
        GrpcServer grpcServer = new GrpcServer();
        grpcServer.startServer();
    }
}
