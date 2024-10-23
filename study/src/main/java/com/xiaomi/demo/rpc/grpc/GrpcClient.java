package com.xiaomi.demo.rpc.grpc;

import com.xiaomi.demo.proto.BookServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/19
 */
@Slf4j
@Data
public class GrpcClient {
    private ManagedChannel managedChannel;
    private BookServiceGrpc.BookServiceStub service;

    public GrpcClient() {
        managedChannel = ManagedChannelBuilder.forAddress("localhost", 6333).useTransportSecurity().build();
        // 开启SSL安全链接
//        managedChannel = NettyChannelBuilder.forAddress("localhost", 6333).useTransportSecurity().sslContext().build();
        service = BookServiceGrpc.newStub(managedChannel).withCallCredentials(.withCallCredentials(new JwtCredential("eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYXZhYm95In0.IMMp7oh1dl_trUn7sn8qiv9GtO-COQyCGDz_Yy8VI4fIqUcRfwQddP45IoxNovxL")))
        ;
    }
}
