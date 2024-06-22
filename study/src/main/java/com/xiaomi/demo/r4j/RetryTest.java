package com.xiaomi.demo.r4j;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
@Slf4j
public class RetryTest {
    RetryConfig config = RetryConfig.custom()
            .maxAttempts(2)
            .waitDuration(Duration.ofSeconds(2))
            .retryOnException(e -> e instanceof RuntimeException)
            .retryExceptions(IOException.class, TimeoutException.class)
            .ignoreExceptions(IllegalArgumentException.class)
            .build();

    Retry retry = Retry.of("default", config);


    @Test
    public void test1() {
        retry.executeRunnable(() -> {
            log.info("start");
            throw new IllegalArgumentException();
        });
    }

    @Test
    public void test2() {
        retry.executeRunnable(() -> {
            log.info("start");
            throw new RuntimeException("123");
        });
    }

    @Test
    public void test3() throws Exception {

        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(1000);
        policy.setMultiplier(2);

        RetryTemplate template = new RetryTemplateBuilder()
                .maxAttempts(4)
                .customBackoff(policy)
                .retryOn(RuntimeException.class)
                .build();
        template.execute((RetryCallback<Object, Exception>) context -> {
            log.info("do start");
            throw new RuntimeException();
        });
    }
}
