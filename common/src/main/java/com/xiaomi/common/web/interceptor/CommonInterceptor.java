package com.xiaomi.common.web.interceptor;

import com.xiaomi.common.http.Constants;
import com.xiaomi.common.utils.JsonUtils;
import com.xiaomi.common.web.constants.HttpCode;
import com.xiaomi.common.web.result.JsonResult;

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