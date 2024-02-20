package com.xiaomi.common.http;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nonnull;
import java.io.IOException;


/**
 * xiwon数据API 拦截器
 *
 * @Author: zhouhaizhen
 * @Date: 2023/11/14
 */
@Slf4j
public class XWAuthInterceptor implements Interceptor {
    private final String appId;
    private final String appSecret;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public XWAuthInterceptor(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @Nonnull
    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(addAuthParam(request));
    }

    private Request addAuthParam(Request request) {
        if ("POST".equals(request.method())) {
            long timeMillis = System.currentTimeMillis();
            Headers.Builder headers = new Headers.Builder();
            headers.add("appId", appId);
            headers.add("sign", SecureUtil.md5(appId + appSecret + timeMillis));
            headers.add("requestTime", String.valueOf(timeMillis));
            return request.newBuilder().headers(headers.build()).build();
        } else {
            return request;
        }

    }
}
