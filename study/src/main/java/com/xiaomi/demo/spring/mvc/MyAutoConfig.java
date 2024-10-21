package com.xiaomi.demo.spring.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * @Author: liuchiyun
 * @Date: 2024/10/21
 */
@Slf4j
public class MyAutoConfig {
    @Bean
    public String init() {
        log.info("MyAutoConfig init");
        return "hello";
    }
}
