package com.xiaomi.demo.jvm;

import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/14
 * -XX:+PrintGCDetails
 * -XX:+PrintGCTimeStamps
 * -XX:+PrintGCDateStamps
 * -Xloggc:logs/gc.log
 * -XX:+PrintTenuringDistribution
 * -XX:+UseG1GC
 * -Xmx10M
 * -Xms10M
 */
public class GarbageCollector {

    private static final int _1MB = 1024 * 1024;

    @Test
    public void test1() {
        byte[] array = new byte[5 * _1MB];
        array = null;
        System.gc();
        array = new byte[5 * _1MB];
        System.out.println(array.length);
    }
}
