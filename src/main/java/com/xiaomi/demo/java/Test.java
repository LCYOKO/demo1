package com.xiaomi.demo.java;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/13
 */
@Slf4j
public class Test {

    @org.junit.Test
    public void testFuture() throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("hhahaha");
            throw new RuntimeException("error");
        }).whenComplete((unused, throwable) -> {
            log.info("complete");
        }).exceptionally(throwable -> {
            log.error("exeception", throwable);
            return null;
        }).get();

        CompletableFuture.allOf().join();

    }
}
