package com.xiaomi.demo.thrift;

import com.google.common.collect.Lists;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

import java.util.List;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 8:49 下午
 */
public class MyBookServiceAsync implements BookService.AsyncIface {
    @Override
    public void getBookInfo(int bookId, AsyncMethodCallback<BookInfo> resultHandler) throws TException {
       resultHandler.onComplete(BookUtil.generateBookInfo());
    }

    @Override
    public void getAllBook(AsyncMethodCallback<List<Book>> resultHandler) throws TException {
         resultHandler.onComplete(Lists.newArrayList(BookUtil.generateBook()));
    }
}
