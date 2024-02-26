package com.xiaomi.demo.java.basic;

import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/21
 */
public class TestConcurrent {
    private volatile int num = 1;

    @Test
    public void test1() {
        // 判断是否中断，会清除中断标识
        Thread.interrupted();
        // 判断是否中断，不会清除中断
        Thread.currentThread().isInterrupted();
    }

    @Test
    public void test2() throws InterruptedException {
        //当前线程阻塞在操作线程上，并等待
        Thread.currentThread().join();
    }

    @Test
    public void test3() {
        // 线程安全
        // 1 原子性
        // 2 可见性
        // 3 原子性


        // volatile
        // 可见性&&有序性
        num++;
    }

    @Test
    public void test4() {
//        ExecutorUtil.gracefulShutdown();
    }
}
