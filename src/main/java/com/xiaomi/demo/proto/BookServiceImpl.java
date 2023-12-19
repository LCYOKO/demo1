package com.xiaomi.demo.proto;


import com.google.common.collect.Lists;
import io.grpc.stub.StreamObserver;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 10:24 下午
 */
public class BookServiceImpl extends BookServiceGrpc.BookServiceImplBase {

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

    @Override
    public void getBooks1(BookRequest request, StreamObserver<BookResponse> responseObserver) {
        Book.Builder javaBook = Book.newBuilder().setBookId(request.getBookId()).setBookName("java");
        BookResponse response = BookResponse.newBuilder().addBooks(javaBook).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getBooks2(BookRequest request, StreamObserver<BookResponse> responseObserver) {
        Book.Builder javaBook = Book.newBuilder().setBookId(request.getBookId()).setBookName("java");
        BookResponse response = BookResponse.newBuilder().addBooks(javaBook).build();
        //服务端响应多条数据
        responseObserver.onNext(response);
        responseObserver.onNext(response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<BookRequest> getBooks3(StreamObserver<BookResponse> responseObserver) {
        return new StreamObserver<BookRequest>() {
            @Override
            public void onNext(BookRequest bookRequest) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };
    }

    @Override
    public StreamObserver<BookRequest> getBooks4(StreamObserver<BookResponse> responseObserver) {
        return new StreamObserver<BookRequest>() {
            @Override
            public void onNext(BookRequest bookRequest) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        }
    }

}
