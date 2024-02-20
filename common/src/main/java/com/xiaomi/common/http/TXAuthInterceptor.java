package com.xiaomi.common.http;

import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaomi.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;

/**
 * 拓兴数据API 拦截器
 *
 * @Author: liuchiyun
 * @Date: 2023/10/7
 */
@Slf4j
public class TXAuthInterceptor implements Interceptor {
    private final String appKey;
    private final String appSecret;
    private static final String SN_PARAM = "devSns";

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public TXAuthInterceptor(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    @Nonnull
    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(addAuthParam(request));
    }

    private Request addAuthParam(Request request) throws IOException {
        if ("POST".equals(request.method())) {
            RequestBody body = request.body();
            if (body == null) {
                return request;
            }
            long timeMillis = System.currentTimeMillis();
            Map<String, Object> authParamMap;
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            String oldParamsJson = buffer.readUtf8();
            authParamMap = JsonUtils.as(oldParamsJson, new TypeReference<Map<String, Object>>() {
            });
            Object devSns = authParamMap.get(SN_PARAM);
            authParamMap.put("appKey", appKey);
            authParamMap.put("signature", SecureUtil.md5(appSecret + devSns + timeMillis));
            authParamMap.put("timestemp", timeMillis);
            return request.newBuilder().post(RequestBody.create(JsonUtils.toString(authParamMap), Constants.MEDIA_JSON)).build();
        } else {
            return request;
        }

    }

    private String bodyToString(final Request request) {
        final Request copy = request.newBuilder().build();
        final Buffer buffer = new Buffer();
        try {
            copy.body().writeTo(buffer);
        } catch (IOException e) {
            return "something error,when show requestBody";
        }
        return buffer.readUtf8();
    }
}
