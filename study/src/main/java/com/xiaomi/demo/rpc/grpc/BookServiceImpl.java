package com.xiaomi.demo.rpc.grpc;


import com.google.common.collect.Lists;
import com.xiaomi.demo.proto.*;
import io.grpc.ServerInterceptor;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 10:24 下午
 */
@Slf4j
public class BookServiceImpl extends BookServiceGrpc.BookServiceImplBase {

    private BookResponse getBookResponse() {
        return BookResponse.newBuilder()
                .setStatus(Status.SUCCESS)
                .addAllBooks(Lists.newArrayList(getBook(), getBook()))
                .build();
    }

    private Book getBook() {
        return Book.newBuilder()
                .setId(1L)
                .setName("java")
                .build();
    }

    @Override
    public void getBooks1(BookRequest request, StreamObserver<BookResponse> responseObserver) {
        Book.Builder javaBook = Book.newBuilder().setId(request.getId()).setName("java");
        BookResponse response = BookResponse.newBuilder().addBooks(javaBook).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getBooks2(BookRequest request, StreamObserver<BookResponse> responseObserver) {
        Book.Builder javaBook = Book.newBuilder().setId(request.getId()).setName("java");
        BookResponse response = BookResponse.newBuilder().addBooks(javaBook).build();
        //服务端响应多条数据
        responseObserver.onNext(response);
        try {
            Thread.sleep(1000);
            responseObserver.onNext(response);
            Thread.sleep(1000);
        } catch (Exception ex) {
            log.error("error", ex);
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<BookRequest> getBooks3(StreamObserver<BookResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(BookRequest request) {
                log.info("request:{}", request);
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                Book.Builder javaBook = Book.newBuilder().setId(1L).setName("java");
                BookResponse response = BookResponse.newBuilder().addBooks(javaBook).build();
                //WARING response只能响应一次
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<BookRequest> getBooks4(StreamObserver<BookResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(BookRequest request) {
                log.info("request:{}", request);
                Book.Builder javaBook = Book.newBuilder().setId(1L).setName("java");
                BookResponse response = BookResponse.newBuilder().addBooks(javaBook).build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

}
