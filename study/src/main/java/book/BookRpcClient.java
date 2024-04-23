package book;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.ExecutionException;

/**
 * @Author: liuchiyun
 * @Date: 2024/4/23
 */
public class BookRpcClient {
    private ManagedChannel managedChannel;
    private BookServiceGrpc.BookServiceFutureStub service;

    public BookRpcClient() {
        managedChannel = ManagedChannelBuilder.forAddress("localhost", 8972).usePlaintext().build();
        service = BookServiceGrpc.newFutureStub(managedChannel);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Book.BookResponse bookResponse = new BookRpcClient().service.getBook(Book.BookRequest.newBuilder().build()).get();
        System.out.println(bookResponse);
        Thread.sleep(10000);
    }
}
