package com.xiaomi.demo.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(value = UserProperties.class)
@Slf4j
@ConditionalOnProperty(value = "conf.enabled", havingValue = "true", matchIfMissing = false)
public class PropertiesConfig {

    @Resource
    private UserProperties userProperties;

    @PostConstruct
    public void init() {
        log.info("userProperties:{}", userProperties);
    }
}
