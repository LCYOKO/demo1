package com.xiaomi.demo.java;


import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/13
 * https://tech.meituan.com/2022/05/12/principles-and-practices-of-completablefuture.html
 */
@Slf4j
public class TestCompeteFuture {

    @Test
    public void testFuture1() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("hhahaha");
            throw new RuntimeException("error");
        }).whenComplete((unused, throwable) -> log.info("complete")).exceptionally(throwable -> {
            log.error("exeception", throwable);
            return null;
        });
        CompletableFuture.allOf(future).join();
    }

    @Test
    public void testFuture2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        CompletableFuture<String> result = future.thenApply(s -> {
            return s + "123";
        });
        future.complete("123");
        log.info("value:{}, result:{}", future.get(), result.get());
    }

    @Test
    public void testFuture3() throws ExecutionException, InterruptedException {
        ExecutorService threadPool1 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync 执行线程:{}", Thread.currentThread().getName());
            return "";
        }, threadPool1);

        future1.thenApply(value -> {
            log.info("thenApply 执行线程:{}", Thread.currentThread().getName());
            return value + "1";
        });
        future1.thenApplyAsync(value -> {
            log.info("thenApplyAsync1, 执行线程:{}", Thread.currentThread().getName());
            return value + "1";
        });
        future1.thenApplyAsync(value -> {
            log.info("thenApplyAsync2, 执行线程:{}", Thread.currentThread().getName());
            return value + "1";
        }, threadPool1);
    }

    @Test
    public void testDeadLock() {
        ExecutorService threadPool1 = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
        CompletableFuture cf1 = CompletableFuture.supplyAsync(() -> {
            //do sth
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("child");
                return "child";
            }, threadPool1).join();
        }, threadPool1);
        cf1.join();
    }

    @Test
    public void testGuava() {
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(threadPool);
        ListenableFuture<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                log.error("exeception", e);
            }
            log.info("hhahaha");
            return "123";
        });

        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                log.info("result");
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("error", t);
            }
        }, executorService);
    }
}
