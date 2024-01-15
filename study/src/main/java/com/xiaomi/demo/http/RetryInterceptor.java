package com.xiaomi.demo.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * @Author: liuchiyun
 * @Date: 2023/10/7
 */
@Slf4j
public class RetryInterceptor implements Interceptor {
    private final int maxRetries;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public RetryInterceptor(int maxRetries) {
        if (maxRetries <= 0) {
            throw new IllegalArgumentException("invalid maxRetries: " + maxRetries);
        }
        this.maxRetries = maxRetries;
    }

    @Nonnull
    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        Request request = chain.request();
        // 进行重试
        for (int retry = 0; retry < maxRetries; retry++) {
            try {
                return chain.proceed(request);
            } catch (IOException e) {
                log.error("重试失败{}次，异常为:", retry, e);
            }
        }
        throw new RemoteException("请求发送失败");
    }
}
