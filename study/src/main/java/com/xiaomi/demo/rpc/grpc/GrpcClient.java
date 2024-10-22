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
        managedChannel = ManagedChannelBuilder.forAddress("localhost", 6333).usePlaintext().build();
        service = BookServiceGrpc.newStub(managedChannel).withOption()
        BookServiceGrpc.
    }
}
