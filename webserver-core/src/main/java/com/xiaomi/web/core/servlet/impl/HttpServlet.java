package com.xiaomi.web.core.servlet.impl;


import com.xiaomi.web.core.enumeration.RequestMethod;
import com.xiaomi.web.core.exception.base.ServletException;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;
import com.xiaomi.web.core.servlet.Servlet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by SinjinSong on 2017/7/21.
 * 根Servlet，实现了不同HTTP方法的路由
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
        if (request.getMethod() == RequestMethod.GET) {
            doGet(request, response);
        } else if (request.getMethod() == RequestMethod.POST) {
            doPost(request, response);
        } else if (request.getMethod() == RequestMethod.PUT) {
            doPut(request, response);
        } else if (request.getMethod() == RequestMethod.DELETE) {
            doDelete(request, response);
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
