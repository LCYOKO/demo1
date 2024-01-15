package com.xiaomi.demo.proto;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/19
 */
@Slf4j
public class GrpcClient {
    private ManagedChannel managedChannel;
    private BookServiceGrpc.BookServiceStub service;

    public GrpcClient() {
        managedChannel = ManagedChannelBuilder.forAddress("localhost", 6333).usePlaintext().build();
        service = BookServiceGrpc.newStub(managedChannel);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        GrpcClient grpcClient = new GrpcClient();
        BookServiceGrpc.BookServiceStub service = grpcClient.service;

//        StreamObserver<BookResponse> responseObserver = new StreamObserver<BookResponse>() {
//            @Override
//            public void onNext(BookResponse value) {
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//        };
//        StreamObserver<BookRequest> requestStreamObserver = service.getBooks3(responseObserver);
//        requestStreamObserver.onCompleted();
    }

}
