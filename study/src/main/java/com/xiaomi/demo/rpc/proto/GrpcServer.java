package com.xiaomi.demo.rpc.proto;

import io.grpc.ServerBuilder;
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
            ServerBuilder<?> serverBuilder = ServerBuilder.forPort(6333);
            serverBuilder.addService(new BookServiceImpl());
            io.grpc.Server server = serverBuilder.build();
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
