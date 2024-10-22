/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiaomi.demo.metrics.micrometer;

import cn.hutool.core.net.NetUtil;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
@Configuration
public class MicrometerConfiguration {
    private static final Duration HISTOGRAM_EXPIRY = Duration.ofMinutes(10);
    private static final Duration STEP = Duration.ofSeconds(5);

    @Value("${spring.application.name:default}")
    private String applicationName;
    /**
     * 应用部署环境：TEST（测试）、STAGING（预发）、PROD（生产）
     */
    @Value("${env:TEST}")
    private String env;

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        log.info("Configuring Registry");
        return registry -> registry
                .config()
                .meterFilter(commonTagFilter())
                .meterFilter(mappingFilter());
//                .meterFilter(uriFilter());
    }

    private MeterFilter commonTagFilter() {
        return MeterFilter.commonTags(Arrays.asList(
                Tag.of("application", applicationName),
                Tag.of("host", NetUtil.getLocalhostStr()),
                Tag.of("env", env)));
    }

    private MeterFilter uriFilter() {
        return MeterFilter.deny(id -> {
            String uri = id.getTag("uri");
            log.info("id: [{}]", id);
            return (uri != null && uri.startsWith("/swagger") && uri.startsWith("/manage")) || !id.getName().toLowerCase().startsWith("app-name");
        });
    }

    private MeterFilter mappingFilter() {
        return new MeterFilter() {
            @NotNull
            @Override
            public Meter.Id map(@NotNull Meter.Id id) {
//                if (id.getName().startsWith("http")) {
//                    return id.withName("app-name." + env + "." + id.getName());
//                }
                return id;
            }

            @Override
            public DistributionStatisticConfig configure(@NotNull Meter.Id id, @NotNull DistributionStatisticConfig config) {
                return config.merge(DistributionStatisticConfig.builder()
                        .percentilesHistogram(true)
                        .percentiles(0.5, 0.75, 0.95)
                        .expiry(HISTOGRAM_EXPIRY)
                        .bufferLength((int) (HISTOGRAM_EXPIRY.toMillis() / STEP.toMillis()))
                        .build());
            }
        };
    }
}
