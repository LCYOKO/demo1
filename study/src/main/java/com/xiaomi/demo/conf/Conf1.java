package com.xiaomi.demo.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: liuchiyun
 * @Date: 2024/4/9
 */
@Configuration
@ConditionalOnProperty(name = "env", havingValue = "sv")
@ComponentScan(basePackages = "com.xiaomi.conf")
@Slf4j
public class Conf1 {
    @PostConstruct
    public void init() {
        log.info("init env sv");
    }
}
