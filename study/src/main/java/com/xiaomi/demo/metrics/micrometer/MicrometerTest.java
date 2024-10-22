package com.xiaomi.demo.metrics.micrometer;

import com.google.common.collect.ImmutableList;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuchiyun
 * @Date: 2024/10/22
 */
@Slf4j
public class MicrometerTest {

    private final MeterRegistry registry = new SimpleMeterRegistry();

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

    @Before
    public void before() {
        List<Tag> commonTags = ImmutableList.of(
                Tag.of("appName", "demo"),
                Tag.of("env", "dev"),
                Tag.of("host", "localhost")
        );
        registry.config().commonTags(commonTags);
    }

    /**
     * 单调递增值
     */
    @Test
    public void testCounter() {
        Counter counter = registry.counter("total.api");
        counter.increment(2);
        log.info("counter: {}", counter.measure());
    }

    /**
     * 当前值
     */
    @Test
    public void testGauge() {
        registry.gauge("taskSize", threadPoolExecutor, (threadPoolExecutor) -> threadPoolExecutor.getQueue().size());
    }

    public void testTimer() {

    }
}
