package com.xiaomi.demo.spring.mvc;

import com.xiaomi.demo.utils.JsonUtils;
import com.xiaomi.demo.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/11
 */
@Slf4j
//@RestControllerAdvice
public class LogResponseAdvice implements ResponseBodyAdvice<Object> {
    @Value("${http.respLog.enabled:true}")
    private boolean logEnabled;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (logEnabled && request instanceof ServletServerHttpRequest && response instanceof ServletServerHttpResponse) {
            log(((ServletServerHttpRequest) request).getServletRequest(), ((ServletServerHttpResponse) response).getServletResponse(), body);
        }
        return body;
    }


    private void log(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        //只有白名单中出现的账户才会打印其所有的返回数据
        String logString = TextUtils.format("Request: [{}] {}?{}", httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI(),
                httpServletRequest.getQueryString());
        String requestBody = getRequestBody(httpServletRequest);
        if (!StringUtils.isEmpty(requestBody)) {
            logString = logString + "\n\tbody: " + requestBody;
        }

        logString = logString + "\nResponse: status: " + httpServletResponse.getStatus() + ", body: " + JsonUtils.toString(body);

        log.info(logString);
    }

    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            try {
                requestBody = new String(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            } catch (IOException e) {
                log.warn("get request body data error", e);
            }
        }
        return requestBody;
    }
}
