package com.xiaomi.demo.java.basic;


import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                Thread.sleep(10000);
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
