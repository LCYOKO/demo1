package com.xiaomi.web.core.servlet;

import com.xiaomi.web.core.exception.ServletException;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;

import java.io.IOException;

/**
 * @Author：liuchiyun
 */
public interface Servlet {
    /**
     * 初始化方法
     */
    void init();

    /**
     * 关闭方法
     */
    void destroy();

    /**
     * 业务处理
     */
    void service(Request request, Response response) throws ServletException, IOException;
}
