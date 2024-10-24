package com.xiaomi.demo.rpc.grpc;

import com.xiaomi.demo.proto.BookRequest;
import com.xiaomi.demo.proto.BookResponse;
import com.xiaomi.demo.proto.BookServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

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
        // 开启SSL安全链接
//        managedChannel = NettyChannelBuilder.forAddress("localhost", 6333).useTransportSecurity().sslContext().build();
//        service = BookServiceGrpc.newStub(managedChannel).withCallCredentials(new JwtCredential("eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYXZhYm95In0.IMMp7oh1dl_trUn7sn8qiv9GtO-COQyCGDz_Yy8VI4fIqUcRfwQddP45IoxNovxL"));
        service = BookServiceGrpc.newStub(managedChannel).withInterceptors(new MyGrpcClientInterceptor());
    }

    public static void main(String[] args) throws InterruptedException {
        GrpcClient client = new GrpcClient();
        BookRequest request = BookRequest.newBuilder().setId(1L).build();
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<BookResponse> streamObserver = new StreamObserver<>() {

            @Override
            public void onNext(BookResponse bookResponse) {
                log.info("receive Response:{}", bookResponse.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("getBooks failed.", throwable);
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };
        client.service.getBooks1(request, streamObserver);
        latch.await();
    }
}
