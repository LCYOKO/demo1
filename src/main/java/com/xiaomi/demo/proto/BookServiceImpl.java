package com.xiaomi.demo.proto;


import com.google.common.collect.Lists;
import io.grpc.stub.StreamObserver;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 10:24 下午
 */
public class BookServiceImpl extends BookServiceGrpc.BookServiceImplBase {
    @Override
    public void getBooks(BookRequest request, StreamObserver<BookResponse> responseObserver) {
        responseObserver.onNext(getBookResponse());
        responseObserver.onCompleted();
    }

    private BookResponse getBookResponse() {
        return BookResponse.newBuilder()
                .setStatus(Status.SUCCESS)
                .addAllBooks(Lists.newArrayList(getBook(), getBook()))
                .build();
    }

    private Book getBook() {
        return Book.newBuilder()
                .setBookId(1L)
                .setBookName("java")
                .build();
    }
}
