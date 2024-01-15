package com.xiaomi.demo.web.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/1
 */
public interface AuthService {

    /**
     * @param request HttpServletRequest
     * @return 请求是否合法
     */
    boolean isValid(HttpServletRequest request);
}
