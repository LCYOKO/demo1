package com.xiaomi.demo.proto;


import com.google.common.collect.Lists;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 10:24 下午
 */
public class BookServiceImpl extends BookService {

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
    public void getBooks(RpcController controller, BookRequest request, RpcCallback<BookResponse> done) {
        request.getBookId();
    }
}
