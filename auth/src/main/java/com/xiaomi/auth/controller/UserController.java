package com.xiaomi.auth.controller;

//import cn.dev33.satoken.stp.StpUtil;
//import cn.dev33.satoken.util.SaResult;
//import com.xiaomi.auth.req.UserLoginReq;
//import com.xiaomi.common.web.result.JsonResult;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @Author: liuchiyun
// * @Date: 2024/1/16
// */
//@RestController
//@RequestMapping("/auth-sa")
//public class UserController {
//
//    @Value("${sa.username}")
//    private String userName;
//    @Value("${sa.password}")
//    private String password;
//
//    @PostMapping("/login")
//    public JsonResult login(@RequestBody UserLoginReq req) {
//        if (StpUtil.isLogin()) {
//            return JsonResult.error("请勿重复登录");
//        }
//        if (userName.equals(req.getUsername()) && password.equals(req.getPassword())) {
//            StpUtil.login(1);
//            return JsonResult.ok(StpUtil.getTokenInfo());
//        } else {
//            return JsonResult.error("用户密码或密码错误");
//        }
//    }
//
//    @GetMapping("/is-login")
//    public JsonResult<Boolean> isLogin() {
//        return JsonResult.ok(StpUtil.isLogin());
//    }
//}
