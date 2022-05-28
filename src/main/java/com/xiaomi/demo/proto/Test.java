package com.xiaomi.demo.proto;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.Iterator;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 10:23 下午
 */
public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {
startClient();
    }

    public static void startClient(){
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("127.0.0.1",9999)
                .usePlaintext()
                .build();
        BookServiceGrpc.BookServiceBlockingStub blockingStub = BookServiceGrpc.newBlockingStub(channel);
        Iterator<BookResponse> books = blockingStub.getBooks(BookRequest.newBuilder().setBookId(1L).build());
        System.out.println(books.next());
    }

    public static void startServer() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(9999)
                .addService(new BookServiceImpl())
                .build();
        server.start();
        server.awaitTermination();
    }
}
