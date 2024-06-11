package com.xiaomi.auth.controller;


import com.xiaomi.pojo.UserDto;
import com.xiaomi.service.UserService;
import org.apache.dubbo.common.constants.LoadbalanceRules;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuchiyun
 * @Date: 2024/5/27
 */
@RestController
@RequestMapping("/dubbo")
public class DubboCtrl {

    @DubboReference(loadbalance = LoadbalanceRules.ROUND_ROBIN)
    private UserService userService;

    @GetMapping("/user")
    public UserDto get() {
        return userService.getById(1L);
    }
}
