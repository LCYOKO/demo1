package com.xiaomi.common.http;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaomi.demo.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xiaomi.demo.http.Constants.MEDIA_JSON;

/**
 * @Author: liuchiyun
 * @Date: 2023/10/7
 * 目前只支持GET/POST请求， MEDIA只支持JSON
 */

@Slf4j
public class YhHttpClient {

    private OkHttpClient client;

    private static final int DEFAULT_RETRY_COUNT = 3;

    private static final Duration DEFAULT_CALL_TIME_OUT = Duration.of(3L, ChronoUnit.SECONDS);

    private static final Interceptor DEFAULT_RETRY_INTERCEPTOR = new RetryInterceptor(DEFAULT_RETRY_COUNT);

    private static final int DEFAULT_MAX_CONNECTIONS = 50;

    private static final long DEFAULT_MAX_CONNECTION_KEEP_ALIVE = 3;

    private static final TimeUnit DEFAULT_MAX_CONNECTION_KEEP_ALIVE_UNIT = TimeUnit.MINUTES;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public YhHttpClient() {
        init(Collections.singletonList(DEFAULT_RETRY_INTERCEPTOR), DEFAULT_CALL_TIME_OUT, DEFAULT_MAX_CONNECTIONS, DEFAULT_MAX_CONNECTION_KEEP_ALIVE, DEFAULT_MAX_CONNECTION_KEEP_ALIVE_UNIT);
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public YhHttpClient(List<Interceptor> interceptors, Duration callTimeOut, int maxConnection, long maxConnectionKeepAlive, TimeUnit maxConnectionKeepAliveTimeUnit) {
        init(interceptors, callTimeOut, maxConnection, maxConnectionKeepAlive, maxConnectionKeepAliveTimeUnit);
    }

    private void init(List<Interceptor> interceptors, Duration callTimeOut, int maxConnection, long maxConnectionKeepAlive, TimeUnit maxConnectionKeepAliveTimeUnit) {
        ConnectionPool connectionPool = new ConnectionPool(maxConnection, maxConnectionKeepAlive, maxConnectionKeepAliveTimeUnit);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .callTimeout(callTimeOut)
                .connectionPool(connectionPool);
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        client = builder.build();
    }

    /**
     * @param url           url
     * @param typeReference 泛型类
     * @param <T>           泛型类型
     * @return 相关类
     */
    public <T> T get(@Nonnull String url, @Nonnull TypeReference<T> typeReference) {
        return get(url, Collections.emptyMap(), Collections.emptyMap(), typeReference);
    }

    /**
     * @param url           url
     * @param typeReference 泛型类
     * @param params        请求参数
     * @param <T>           泛型类型
     * @return 相关类
     */
    public <T> T get(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull TypeReference<T> typeReference) {
        return get(url, params, Collections.emptyMap(), typeReference);
    }

    /**
     * @param url           url
     * @param typeReference 泛型类
     * @param params        请求参数
     * @param headers       请求头
     * @param <T>           泛型类型
     * @return 相关类
     */
    public <T> T get(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Map<String, String> headers, @Nonnull TypeReference<T> typeReference) {
        return send(buildGetRequest(url, params, headers), typeReference);
    }

    /**
     * @param url   url
     * @param clazz 泛型类
     * @param <T>   泛型类型
     * @return 相关类
     */
    public <T> T get(@Nonnull String url, @Nonnull Class<T> clazz) {
        return get(url, Collections.emptyMap(), Collections.emptyMap(), clazz);
    }

    public byte[] get(@Nonnull String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            log.info("request:{}, response:{}", request, response);
            int code = response.code();
            if (code != HttpStatus.HTTP_OK) {
                throw new RuntimeException("response status is not ok");
            }
            ResponseBody responseBody = response.body();
            Headers headers = response.headers();
            return responseBody != null ? responseBody.bytes() : null;
        } catch (Exception exception) {
            log.error("send request:{} failed", request);
            throw new RuntimeException(exception);
        }
    }

    /**
     * @param url    url
     * @param params 请求参数
     * @param clazz  泛型类
     * @param <T>    泛型类型
     * @return 相关类
     */
    public <T> T get(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Class<T> clazz) {
        return get(url, params, Collections.emptyMap(), clazz);
    }

    /**
     * @param url     url
     * @param params  请求参数
     * @param headers 请求头
     * @param clazz   泛型类
     * @param <T>     泛型类型
     * @return 相关类
     */
    public <T> T get(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Map<String, String> headers, @Nonnull Class<T> clazz) {
        return send(buildGetRequest(url, params, headers), clazz);
    }

    private Request buildGetRequest(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Map<String, String> headers) {
        Request.Builder builder = new Request.Builder();
        for (String header : headers.keySet()) {
            builder.addHeader(header, headers.get(header));
        }
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            httpUrlBuilder.addQueryParameter(param.getKey(), param.getValue().toString());
        }
        return builder.url(httpUrlBuilder.build()).get().build();
    }

    /**
     * @param url           url
     * @param typeReference 泛型类
     * @param <T>           泛型类型
     * @return 相关类
     */
    public <T> T post(@Nonnull String url, @Nonnull TypeReference<T> typeReference) {
        return post(url, Collections.emptyMap(), Collections.emptyMap(), typeReference);
    }

    /**
     * @param url           url
     * @param typeReference 泛型类
     * @param params        请求参数
     * @param <T>           泛型类型
     * @return 相关类
     */
    public <T> T post(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull TypeReference<T> typeReference) {
        return post(url, params, Collections.emptyMap(), typeReference);
    }

    /**
     * @param url           url
     * @param typeReference 泛型类
     * @param params        请求参数
     * @param headers       请求头
     * @param <T>           泛型类型
     * @return 相关类
     */
    public <T> T post(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Map<String, String> headers, @Nonnull TypeReference<T> typeReference) {
        return send(buildPostRequest(url, params, headers), typeReference);
    }

    /**
     * @param url   url
     * @param clazz 泛型类
     * @param <T>   泛型类型
     * @return 相关类
     */
    public <T> T post(@Nonnull String url, @Nonnull Class<T> clazz) {
        return post(url, Collections.emptyMap(), Collections.emptyMap(), clazz);
    }

    /**
     * @param url    url
     * @param params 请求参数
     * @param clazz  泛型类
     * @param <T>    泛型类型
     * @return 相关类
     */
    public <T> T post(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Class<T> clazz) {
        return post(url, params, Collections.emptyMap(), clazz);
    }

    /**
     * post - 表单提交
     *
     * @param url    url
     * @param params 请求参数
     * @param clazz  泛型类
     * @param <T>    泛型类型
     * @return 相关类
     */
    public <T> T postFormData(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Class<T> clazz) {
        return postFormData(url, params, Collections.emptyMap(), clazz);
    }

    /**
     * @param url     url
     * @param params  请求参数
     * @param headers 请求头
     * @param clazz   泛型类
     * @param <T>     泛型类型
     * @return 相关类
     */
    public <T> T post(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Map<String, String> headers, @Nonnull Class<T> clazz) {
        return send(buildPostRequest(url, params, headers), clazz);
    }

    public <T> T postFormData(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Map<String, String> headers, @Nonnull Class<T> clazz) {
        return send(buildPostFormDataRequest(url, params, headers), clazz);
    }

    private Request buildPostRequest(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Map<String, String> headers) {
        Request.Builder builder = new Request.Builder();
        for (String header : headers.keySet()) {
            builder.addHeader(header, headers.get(header));
        }
        return builder.url(url).post(RequestBody.create(JsonUtils.toString(params), MEDIA_JSON)).build();
    }

    private Request buildPostFormDataRequest(@Nonnull String url, @Nonnull Map<String, Object> params, @Nonnull Map<String, String> headers) {
        Request.Builder builder = new Request.Builder();
        for (String header : headers.keySet()) {
            builder.addHeader(header, headers.get(header));
        }
        FormBody.Builder formBody = new FormBody.Builder();
        params.forEach((key, value) -> {
            formBody.add(key, value.toString());
        });
        FormBody build = formBody.build();
        return builder.url(url).post(build).build();
    }

    private <T> T send(Request request, @Nonnull TypeReference<T> typeReference) {
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            log.info("request:{}, response:{}", request, response);
            int code = response.code();
            if (code != HttpStatus.HTTP_OK) {
                throw new RuntimeException("response status is not ok");
            }
            ResponseBody responseBody = response.body();
            String jsonBody = responseBody == null ? null : new String(responseBody.bytes());
            if (StringUtils.isBlank(jsonBody)) {
                throw new RuntimeException("response body is null");
            }
            log.info("jsonBody:{}", jsonBody);
            return JsonUtils.as(jsonBody, typeReference);
        } catch (Exception exception) {
            log.error("send request:{} failed", request);
            throw new RuntimeException(exception);
        }
    }

    private <T> T send(Request request, @Nonnull Class<T> clazz) {
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            log.info("request:{}, response:{}", request, response);
            int code = response.code();
            if (code != HttpStatus.HTTP_OK) {
                throw new RuntimeException("response status is not ok");
            }
            ResponseBody responseBody = response.body();
            String jsonBody = responseBody == null ? null : new String(responseBody.bytes());
            if (StringUtils.isBlank(jsonBody)) {
                throw new RuntimeException("response body is null");
            }
            log.info("jsonBody:{}", jsonBody);
            return JsonUtils.as(jsonBody, clazz);
        } catch (Exception exception) {
            log.error("send request:{} failed", request);
            throw new RuntimeException(exception);
        }
    }

}
