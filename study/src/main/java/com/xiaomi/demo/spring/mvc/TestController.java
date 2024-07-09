package com.xiaomi.demo.spring.mvc;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @Resource
    private MessageSource messageSource;


    @PostMapping("/filter")
    public String filterTest(String name) {
        return name;
    }

    @GetMapping("/message")
    public Map<String, Object> getMessage(@Valid Param param) {
        return Collections.singletonMap("lcy", param);
    }

    @GetMapping("/message/{local}")
    public String getMessage(@PathVariable("local") String local) {
        return messageSource.getMessage("name", new Object[]{local}, LocaleUtils.toLocale(local));
    }
}
