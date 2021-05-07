package com.xiaomi.demo.base;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @Author：liuchiyun
 * @Date: 2021/4/30
 */
public class TestDate {

    @Test
    public  void test(){
        Date date = new Date(1619767477L*1000);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(date));
    }
}
