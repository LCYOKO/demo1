package com.xiaomi.demo.r4j;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.junit.Test;

import java.io.IOException;
import java.time.Duration;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
public class CircuitBreakerTest {
    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMillis(1000))
            .permittedNumberOfCallsInHalfOpenState(2)
            .slidingWindowSize(2)
            .recordExceptions(IOException.class)
            .ignoreExceptions(IllegalArgumentException.class)
            .build();

    @Test
    public void test1() {
    }
}
