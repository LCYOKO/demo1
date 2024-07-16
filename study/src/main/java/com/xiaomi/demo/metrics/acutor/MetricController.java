package com.xiaomi.demo.metrics.acutor;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/4
 */
@RequestMapping("/metric")
@RestController
@Slf4j
public class MetricController {

    @Autowired
    private MeterRegistry registry;

    @GetMapping("/timing")
    @Timed(value = "test", extraTags = {"test_tag", "test_info"})
    public String testTiming() {
        log.error("timing. traceId:{}", MDC.get("traceId"));
        return "timing";
    }
}
