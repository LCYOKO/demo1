package com.xiaomi.demo.algo;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author liuchiyun
 * @Date 2022/4/7 11:07 下午
 */
@Slf4j
public class ArraySortTest {
    @Test
    public void testMergeSort() throws InterruptedException {
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .build(new com.github.benmanes.caffeine.cache.CacheLoader<String, String>() {
                    @Override
                    public @Nullable String load(@NonNull String key) throws Exception {
                        return loadFunc();
                    }
                });


        log.info("getCache:{}", cache.get("test"));
        Thread.sleep(10000);
        log.info("getCache:{}", cache.get("test"));
    }

    private String loadFunc() {
        log.info("execute loadFunc");
        return "test";
    }
}
