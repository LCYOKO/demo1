package com.xiaomi.demo.java.basic;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/13
 */
@Slf4j
public class TestCompeteFuture {

    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> {
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
        }).get();
        CompletableFuture.allOf().join();
    }
}
