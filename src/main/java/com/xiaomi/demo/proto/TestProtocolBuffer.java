package com.xiaomi.demo.proto;

import com.google.protobuf.StringValue;
import org.junit.Test;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 10:23 下午
 */
public class TestProtocolBuffer {

    @Test
    public void testNewBook() {
        BookRequest.Builder builder = BookRequest.newBuilder();
//        builder.setBookId(1L);
        builder.setBookName((StringValue) null);

        BookRequest request = builder.build();
        System.out.println(request.getBookName().isInitialized());
    }

//    public static void startClient(){
//        ManagedChannel channel = ManagedChannelBuilder
//                .forAddress("127.0.0.1",9999)
//                .usePlaintext()
//                .build();
//        BookServiceGrpc.BookServiceBlockingStub blockingStub = BookServiceGrpc.newBlockingStub(channel);
//        Iterator<BookResponse> books = blockingStub.getBooks(BookRequest.newBuilder().setBookId(1L).build());
//        System.out.println(books.next());
//    }
//
//    public static void startServer() throws IOException, InterruptedException {
//        Server server = ServerBuilder.forPort(9999)
//                .addService(new BookServiceImpl())
//                .build();
//        server.start();
//        server.awaitTermination();
//    }
}
