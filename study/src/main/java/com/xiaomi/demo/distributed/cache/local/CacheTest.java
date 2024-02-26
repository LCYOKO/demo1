package com.xiaomi.demo.distributed.cache.local;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/9
 */
public class CacheTest {
    @Test
    public void testGuavaCache() {
        LoadingCache<String, Optional<String>> cache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, Optional<String>>() {
                    @Override
                    public Optional<String> load(String s) throws Exception {
                        return Optional.empty();
                    }
                });
    }

    @Test
    public void testCaffeineCache() {
        com.github.benmanes.caffeine.cache.LoadingCache<String, Optional<String>> cache = Caffeine.newBuilder()
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .build(key -> Optional.empty());
    }
}
