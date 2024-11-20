package com.xiaomi.demo.rpc;

import com.xiaomi.demo.proto.BookRequest;
import com.xiaomi.demo.proto.BookResponse;
import com.xiaomi.demo.proto.BookServiceGrpc;
import com.xiaomi.demo.rpc.grpc.GrpcClient;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/30
 */
@Slf4j
public class GrpcClientTest {
    GrpcClient grpcClient;

    @Before
    public void before() {
        grpcClient = new GrpcClient();
    }

    @Test
    public void test1() throws InterruptedException {
        BookServiceGrpc.BookServiceStub service = grpcClient.getService();
        BookRequest request = BookRequest.newBuilder().setId(1L).build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        StreamObserver<BookResponse> responseObserver = new StreamObserver<BookResponse>() {
            @Override
            public void onNext(BookResponse value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        };
        service.getBooks1(request, responseObserver);
        countDownLatch.await();
    }

    @Test
    public void test2() throws InterruptedException {
        BookServiceGrpc.BookServiceStub service = grpcClient.getService();
        BookRequest request = BookRequest.newBuilder().setId(1L).build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicInteger count = new AtomicInteger(1);
        StreamObserver<BookResponse> responseObserver = new StreamObserver<BookResponse>() {
            @Override
            public void onNext(BookResponse value) {
                log.info("count:{}, resp:{}", count.getAndIncrement(), value);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                log.info("down");
                countDownLatch.countDown();
            }
        };
        service.getBooks2(request, responseObserver);
        countDownLatch.await();
    }

    @Test
    public void test3() throws InterruptedException {
        BookServiceGrpc.BookServiceStub service = grpcClient.getService();
        BookRequest request = BookRequest.newBuilder().setId(1L).build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicInteger count = new AtomicInteger(1);
        StreamObserver<BookResponse> responseObserver = new StreamObserver<BookResponse>() {
            @Override
            public void onNext(BookResponse value) {
                log.info("count:{}, resp:{}", count.getAndIncrement(), value);
            }

            @Override
            public void onError(Throwable t) {
                log.error("error", t);
            }

            @Override
            public void onCompleted() {
                log.info("down");
                countDownLatch.countDown();
            }
        };
        StreamObserver<BookRequest> requestStreamObserver = service.getBooks3(responseObserver);
        requestStreamObserver.onNext(request);
        requestStreamObserver.onNext(request);
        requestStreamObserver.onNext(request);
        requestStreamObserver.onCompleted();
        countDownLatch.await();
    }

    @Test
    public void test4() throws InterruptedException {
        BookServiceGrpc.BookServiceStub service = grpcClient.getService();
        BookRequest request = BookRequest.newBuilder().setId(1L).build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicInteger count = new AtomicInteger(1);
        StreamObserver<BookResponse> responseObserver = new StreamObserver<BookResponse>() {
            @Override
            public void onNext(BookResponse value) {
                log.info("count:{}, resp:{}", count.getAndIncrement(), value);
            }

            @Override
            public void onError(Throwable t) {
                log.error("error", t);
            }

            @Override
            public void onCompleted() {
                log.info("down");
                countDownLatch.countDown();
            }
        };
        StreamObserver<BookRequest> requestStreamObserver = service.getBooks4(responseObserver);
        requestStreamObserver.onNext(request);
        requestStreamObserver.onNext(request);
        requestStreamObserver.onNext(request);
        requestStreamObserver.onCompleted();
        countDownLatch.await();
    }
}
