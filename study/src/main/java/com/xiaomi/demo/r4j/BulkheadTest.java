package com.xiaomi.demo.r4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Duration;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
@Slf4j
public class BulkheadTest {
    BulkheadConfig config = BulkheadConfig.custom()
            .maxConcurrentCalls(1)
            .maxWaitDuration(Duration.ofMillis(500))
            .build();

    ThreadPoolBulkheadConfig.Builder config1 = ThreadPoolBulkheadConfig.custom()
            .maxThreadPoolSize(1)
            .coreThreadPoolSize(1)
            .queueCapacity(1)
            .keepAliveDuration(Duration.ofMillis(500));

    @Test
    public void test1() throws InterruptedException {
        Bulkhead bulkhead = Bulkhead.of("test1", config);
        bulkhead.executeRunnable(() -> {
            log.info("start 1");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        new Thread(() -> {
            bulkhead.executeRunnable(() -> {
                log.info("start 2");
            });
        }).start();
        Thread.sleep(1000);
    }
}
