package com.xiaomi.demo.conf;


import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Setter;
import org.springframework.boot.info.GitProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: liuchiyun
 * @Date: 2024/5/27
 */
@RestController
@RequestMapping("/nacos")
public class NacosCtrl {
    @NacosValue(value = "${name:lisi}", autoRefreshed = true)
    @Setter
    private String name;

    @Resource
    private GitProperties gitProperties;

    @GetMapping("/name")
    public String getName() {
        return name;
    }

    @GetMapping("/git")
    public GitProperties getGitProperties() {
        return gitProperties;
    }
}