package com.xiaomi.conf;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: liuchiyun
 * @Date: 2024/4/9
 */
@Component
public class TestBean {
    @PostConstruct
    public void init() {
        System.out.println("test bean");
    }
}
