package com.xiaomi.demo.spring.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/9
 */
@RestController
@RequestMapping("/test")
//@Validated
public class TestController {
    @GetMapping("/message")
    public Map<String, Object> getMessage(@Valid Param param) {
        return Collections.singletonMap("lcy", param);
    }
}
