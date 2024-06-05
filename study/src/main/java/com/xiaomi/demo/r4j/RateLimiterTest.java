package com.xiaomi.demo.r4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Duration;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
@Slf4j
public class RateLimiterTest {

    @Test
    public void testRateLimiter() {
        //有2种限流器AtomicRateLimiter和SemaphoreBasedRateLimiter
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(2))
                .limitForPeriod(1)
                .timeoutDuration(Duration.ofSeconds(25))
                .build();
        RateLimiter rateLimiter1 = RateLimiter.of("name1", config);
        log.info("acquire:{}", rateLimiter1.acquirePermission(1));
        log.info("acquire:{}", rateLimiter1.acquirePermission(1));
    }
}
