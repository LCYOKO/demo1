package com.xiaomi.demo.rpc.thrift;

import com.google.common.collect.Lists;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 8:49 下午
 */
public class MyBookServiceAsync implements BookService.AsyncIface {

    @Override
    public void getBookInfo(int bookId, AsyncMethodCallback resultHandler) throws TException {
        resultHandler.onComplete(BookUtil.generateBookInfo());
    }

    @Override
    public void getAllBook(AsyncMethodCallback resultHandler) throws TException {
        resultHandler.onComplete(Lists.newArrayList(BookUtil.generateBook()));
    }
}
