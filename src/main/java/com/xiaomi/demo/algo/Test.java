package com.xiaomi.demo.algo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author liuchiyun
 * @Date 2022/4/7 11:07 下午
 */
public class Test {


    public native void sayHello();

    private static final int _1MB = 1024 * 1024;

    private static int count;

    public static void main(String[] args) throws InterruptedException {
        count = 30;
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> doWhile("A", 0)).start();
        new Thread(() -> doWhile("B", 1)).start();
        new Thread(() -> doWhile("C", 2)).start();
        latch.await();
    }

    public static void doWhile(String symbol, int remain) {
        while (count > 0) {
            if (count % 3 != remain) {
                Thread.yield();
                continue;
            }
            printSymbolAndCountdown(symbol);
        }

    }

    public static synchronized void printSymbolAndCountdown(String symbol) {
        System.out.println(symbol);
        count--;
    }

    public void test(List<? extends Base> list) {
        Base base = list.get(0);
    }

    public void test1() {
    }


    static class Item<T extends Base> {

    }

    static class People extends Base {

    }

    @Data
    static
    class Base {
        private String data;
    }
}
