package com.xiaomi.demo.web.interceptor;


import com.xiaomi.demo.http.Constants;
import com.xiaomi.demo.utils.JsonUtils;
import com.xiaomi.demo.web.constants.HttpCode;
import com.xiaomi.demo.web.result.JsonResult;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/16
 */
public class CommonInterceptor {

    protected void returnError(@Nonnull HttpServletResponse response, HttpCode httpCode) throws IOException {
        response.setContentType(Constants.MEDIA_JSON.toString());
        response.getWriter().print(JsonUtils.toString(JsonResult.error(httpCode)));
        response.flushBuffer();
    }
}