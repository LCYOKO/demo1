package com.xiaomi.web.core.servlet.impl;


import com.xiaomi.web.core.enumeration.HttpStatus;
import com.xiaomi.web.core.exception.ServletException;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;
import com.xiaomi.web.core.servlet.Servlet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author：liuchiyun 根Servlet，实现了不同HTTP方法的路由
 */
@Slf4j
public abstract class HttpServlet implements Servlet {

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void service(Request request, Response response) throws ServletException, IOException {
        switch (request.getMethod()) {
            case GET:
                doGet(request, response);
                break;
            case POST:
                doPost(request, response);
                break;
            case PUT:
                doPut(request, response);
                break;
            case DELETE:
                doDelete(request, response);
                break;
            default:
                throw new ServletException(HttpStatus.BAD_REQUEST, "invalid requestMethod:" + request.getMethod());
        }
    }

    public void doGet(Request request, Response response) throws ServletException, IOException {
    }

    public void doPost(Request request, Response response) throws ServletException, IOException {
    }

    public void doPut(Request request, Response response) throws ServletException, IOException {
    }

    public void doDelete(Request request, Response response) throws ServletException, IOException {
    }


}
