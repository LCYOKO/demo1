package com.xiaomi.demo.java.basic;

import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/26
 */
public class TestGarbageCollector {
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
