package com.xiaomi.demo.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Optional;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/9
 */
public class Test {
    @org.junit.Test
    public void testGuavaCache() {
        LoadingCache<String, Optional<String>> cache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, Optional<String>>() {

                    @Override
                    public Optional<String> load(String s) throws Exception {
                        return Optional.empty();
                    }
                });
    }
}
