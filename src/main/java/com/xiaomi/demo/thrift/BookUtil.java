package com.xiaomi.demo.thrift;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 8:50 下午
 */
public class BookUtil {

    public static Book generateBook(){
        Book book = new Book();
        book.setBookId(1111);
        book.setInfo(generateBookInfo());
        return book;
    }

    public static BookInfo generateBookInfo(){
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookId(1);
        bookInfo.setBookName("java");
        return bookInfo;
    }
}
