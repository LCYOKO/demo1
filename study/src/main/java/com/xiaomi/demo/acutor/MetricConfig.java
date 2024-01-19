package com.xiaomi.demo.acutor;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/4
 */
@Configuration
public class MetricConfig {
//    @Bean
//    public TimedAspect timedAspect(MeterRegistry meterRegistry) {
//        return new TimedAspect(meterRegistry);
//    }
}
