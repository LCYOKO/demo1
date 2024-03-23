package com.xiaomi.demo.spring.mvc;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Locale;
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

    @GetMapping("/message")
    public Map<String, Object> getMessage(@Valid Param param) {
        return Collections.singletonMap("lcy", param);
    }

    @GetMapping("/message/{local}")
    public String getMessage(@PathVariable("local") String local) {
        return messageSource.getMessage("name", new Object[]{local}, LocaleUtils.toLocale(local));
    }
}
