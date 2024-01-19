package com.xiaomi.web.core.request.dispatcher;

import com.xiaomi.web.core.exception.base.ServletException;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;

import java.io.IOException;

/**
 * Created by SinjinSong on 2017/7/21.
 */
public interface RequestDispatcher {

    void forward(Request request, Response response) throws ServletException, IOException;
}
