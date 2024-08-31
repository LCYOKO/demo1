package com.xiaomi.demo.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
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
        AsyncLoadingCache<String, Optional<String>> loadingCache = Caffeine.newBuilder()
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .buildAsync(new AsyncCacheLoader<String, Optional<String>>() {
                    @Override
                    public @NonNull CompletableFuture<Optional<String>> asyncLoad(@NonNull String key, @NonNull Executor executor) {
                        return CompletableFuture.supplyAsync(() -> Optional.empty(), executor);
                    }
                });
    }
}
