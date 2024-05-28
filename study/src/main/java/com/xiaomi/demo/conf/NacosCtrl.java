package com.xiaomi.demo.conf;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.client.naming.NacosNamingService;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuchiyun
 * @Date: 2024/5/27
 */
@RestController
@RequestMapping("/nacos")
public class NacosCtrl {
    @NacosValue(value = "${name}", autoRefreshed = true)
    @Setter
    private String name;

//    @NacosInjected
//    private NacosNamingService nacosNamingService;

    @GetMapping("/name")
    public String getName() {
        return name;
    }

//    @GetMapping("/service")
//    public String getAll() {
//        return nacosNamingService.getServerStatus();
//    }
}
