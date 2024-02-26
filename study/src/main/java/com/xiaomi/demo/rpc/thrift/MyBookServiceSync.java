package com.xiaomi.demo.rpc.thrift;

import com.google.common.collect.Lists;
import org.apache.thrift.TException;

import java.util.List;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 8:46 下午
 */
public class MyBookServiceSync implements BookService.Iface {
    @Override
    public BookInfo getBookInfo(int bookId) throws TException {
        return BookUtil.generateBookInfo();
    }

    @Override
    public List<Book> getAllBook() throws TException {
        return Lists.newArrayList(BookUtil.generateBook());
    }
}
