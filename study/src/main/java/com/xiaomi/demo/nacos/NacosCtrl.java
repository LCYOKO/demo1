package com.xiaomi.demo.nacos;


////import com.alibaba.nacos.api.config.annotation.NacosValue;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.info.GitProperties;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Author: liuchiyun
// * @Date: 2024/5/27
// */
//@RestController
//@RequestMapping("/nacos")
//public class NacosCtrl {
//    @NacosValue(value = "${name:lisi}", autoRefreshed = true)
//    @Setter
//    private String name;
//
//    @Autowired(required = false)
//    private GitProperties gitProperties;
//
//    @GetMapping("/name")
//    public String getName() {
//        return name;
//    }
//
//    @GetMapping("/git")
//    public GitProperties getGitProperties() {
//        return gitProperties;
//    }
//}
