package com.xiaomi.demo.apollo;

import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/9
 */
@RestController
@RequestMapping("/apollo")
@Slf4j
public class Test {

    @ApolloJsonValue("${key}")
    private String apolloKey;

    @GetMapping("/key")
    public String getValue() {
        log.info("get key");
        return apolloKey;
    }
}
