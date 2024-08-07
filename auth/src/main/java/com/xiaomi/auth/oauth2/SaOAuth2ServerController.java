package com.xiaomi.auth.oauth2;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.stp.StpUtil;
import com.xiaomi.common.web.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/7
 */
@RestController
@Slf4j
public class SaOAuth2ServerController {

    @RequestMapping("/oauth2/*")
    public Object request() {
        log.debug("------- 进入请求: {}", SaHolder.getRequest().getUrl());
        return SaOAuth2Handle.serverRequest();
    }

    @GetMapping("/login")
    public JsonResult<?> login() {
        StpUtil.login(1001);
        return JsonResult.ok();
    }
}
