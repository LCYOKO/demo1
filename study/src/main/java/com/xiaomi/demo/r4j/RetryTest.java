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

import javax.xml.ws.WebServiceException;
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
            .retryOnException(e -> e instanceof WebServiceException)
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
            throw new WebServiceException();
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
                .retryOn(WebServiceException.class)
                .build();
        template.execute(new RetryCallback<Object, Exception>() {

            @Override
            public Object doWithRetry(RetryContext context) {
                log.info("do start");
                throw new WebServiceException();
//                return null;
            }
        });
    }
}
