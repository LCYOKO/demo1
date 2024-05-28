package com.xiaomi.conf;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: liuchiyun
 * @Date: 2024/4/9
 */
@Component
public class TestBean {
    @NacosValue("${name}")
    private String name;

    @PostConstruct
    public void init() {
        System.out.println(name);
    }
}
