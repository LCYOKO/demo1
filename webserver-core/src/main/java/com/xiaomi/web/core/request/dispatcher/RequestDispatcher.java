package com.xiaomi.web.core.request.dispatcher;

import com.xiaomi.web.core.exception.ServletException;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;

import java.io.IOException;

/**
 * @Author：liuchiyun
 */
public interface RequestDispatcher {

    void forward(Request request, Response response) throws ServletException, IOException;
}
