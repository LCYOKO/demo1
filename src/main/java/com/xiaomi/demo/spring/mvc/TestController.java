package com.xiaomi.demo.spring.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/9
 */
@RestController
@RequestMapping("/test-log")
public class TestController {


    @GetMapping("message")
    public Map<String, Object> getMessage() {
        return Collections.singletonMap("lcy","123");
    }
}
