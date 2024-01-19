package com.xiaomi.web.core.servlet;

import com.xiaomi.web.core.exception.base.ServletException;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;

import java.io.IOException;

/**
 * @author sinjinsong
 * @date 2018/5/2
 */
public interface Servlet {
    void init();

    void destroy();

    void service(Request request, Response response) throws ServletException, IOException;
}
