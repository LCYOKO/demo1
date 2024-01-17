package com.xiaomi.auth.controller;

import com.xiaomi.common.web.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/16
 */
@RequestMapping
@RestController("/auth-sa")
public class UserController {

    @PostMapping("/login")
    public JsonResult<Void> login() {
        return JsonResult.ok();
    }
}
