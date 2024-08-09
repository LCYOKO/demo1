package com.xiaomi.demo.proto;

import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @Author: liuchiyun
 * @Date: 2024/4/23
 */
@Slf4j
public class BookRpcClient {
    private final ManagedChannel managedChannel;
    private final BookServiceGrpc.BookServiceFutureStub service;

    public BookRpcClient() {
        managedChannel = ManagedChannelBuilder.forAddress("localhost", 8972).usePlaintext().build();
        service = BookServiceGrpc.newFutureStub(managedChannel);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        BookRequest request = BookRequest.newBuilder().setId(1L).setName(StringValue.of("Java实战")).build();
        BookResponse response = new BookRpcClient().service.getBooks1(request).get();

        log.info("response{}", response);
    }
}
