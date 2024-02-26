package com.xiaomi.demo.metrics.prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/7
 */
@RestController
@RequestMapping("/prometheus")
public class PrometheusController {
    @Autowired(required = false)
    private MeterRegistry meterRegistry;

    @GetMapping("/timer")
    public String testTimer() {
        String[] tags = new String[2];
        tags[0] = "api";
        tags[1] = "/prometheus/timer";
        return "ok";
    }
}
