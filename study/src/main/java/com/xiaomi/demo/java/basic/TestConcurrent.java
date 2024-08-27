package com.xiaomi.demo.java.basic;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/21
 */
@Slf4j
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

    public static InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
    public static TransmittableThreadLocal<Integer> context = new TransmittableThreadLocal<>();

    @Test
    public void test4() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            threadLocal.set(i);
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                log.info("threadName:{}, value:{}", name, threadLocal.get());
                executorService.execute(new BusinessThread(name, false));
            }).start();
        }
        Thread.sleep(10000);
    }

    @Test
    public void test5() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            context.set(i);
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                log.info("threadName:{}, value:{}", name, context.get());
                executorService.execute(TtlRunnable.get(new BusinessThread(name, true)));
            }).start();
        }
        Thread.sleep(10000);
    }

    @Test
    public void test6(){
        new ConcurrentReferenceHashMap<>().put("1", "1");
    }

    @Test
    public void test7() {
        //对象不能共享
        ThreadLocalRandom random = ThreadLocalRandom.current();
        log.info("random:{}", random.nextInt());
    }


    public static class BusinessThread implements Runnable {
        private String parentName;
        private boolean useTtl;

        public BusinessThread(String parentName, boolean useTtl) {
            this.parentName = parentName;
            this.useTtl = useTtl;
        }

        @Override
        public void run() {
            Integer value = useTtl ? context.get() : threadLocal.get();
            log.info("pThreadName:{}, threadName:{}, value:{}", parentName, Thread.currentThread().getName(), value);
        }
    }
}
