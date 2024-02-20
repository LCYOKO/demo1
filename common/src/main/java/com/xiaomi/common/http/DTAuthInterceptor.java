package com.xiaomi.common.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * 德拓信息 授权拦截器
 */
@Slf4j
public class DTAuthInterceptor implements Interceptor {

    private final String appId;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public DTAuthInterceptor(String appId) {
        this.appId = appId;
    }

    @Nonnull
    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(addAuthParam(request));
    }

    private Request addAuthParam(Request request) throws IOException {
        return request.newBuilder().addHeader("appId", appId).build();
    }

}
