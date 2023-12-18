package com.xiaomi.demo.guava;

import com.google.common.base.Charsets;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.util.concurrent.RateLimiter;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/18
 */
public class TestLimiter {
    public void testLimiter() {
        // 不带预热的限流器
        RateLimiter rateLimiter = RateLimiter.create(50);
        // 带预热的限流器
        rateLimiter = RateLimiter.create(50, 1000, TimeUnit.MILLISECONDS);
    }

    public void testCache() {
        LoadingCache<Integer, Optional<String>> graphs = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build(
                        new CacheLoader<Integer, Optional<String>>() {
                            @Override
                            public java.util.Optional<String> load(Integer key) {
                                return java.util.Optional.empty();
                            }
                        });
    }

    @SneakyThrows
    public void testBloomFilter() {
        Funnel<Person> personFunnel = (person, into) -> into.putInt(person.id)
                .putString(person.firstName, Charsets.UTF_8)
                .putString(person.lastName, Charsets.UTF_8)
                .putInt(person.birthYear);

        BloomFilter<Person> friends = BloomFilter.create(personFunnel, 500, 0.01);
//        for (Person friend : friendsList) {
//            friends.put(friend);
//        }
//        if (friends.mightContain(someone)) {
//            // the probability that someone reached this place if they aren't a friend is
//            // 1% we might, for example, start asynchronously loading things for someone
//            // while we do a more expensive exact check
//        }
    }

    @Data
    static class Person {
        private int id;
        private String firstName;
        private String lastName;
        private int birthYear;
    }
}
