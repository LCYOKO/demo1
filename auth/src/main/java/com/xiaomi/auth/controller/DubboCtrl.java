package com.xiaomi.auth.controller;


import com.xiaomi.pojo.UserDto;
import com.xiaomi.service.UserService;
import org.apache.dubbo.common.constants.LoadbalanceRules;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: liuchiyun
 * @Date: 2024/5/27
 */
@RestController
@RequestMapping("/dubbo")
public class DubboCtrl {

    @DubboReference(loadbalance = LoadbalanceRules.ROUND_ROBIN, timeout = 3000)
    private UserService userService;

    @GetMapping("/user")
    public UserDto get() throws ExecutionException, InterruptedException, TimeoutException {
        return userService.getByIdAsync(1L).get(3, TimeUnit.SECONDS);
    }
}
