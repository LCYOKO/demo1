package com.xiaomi.common.web.interceptor;

import com.xiaomi.common.web.annotation.OpenAuth;
import com.xiaomi.common.web.constants.HttpCode;
import com.xiaomi.common.web.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liuchiyun
 * @Date: 2023/10/31
 */
@Slf4j
public class OpenAuthInterceptor extends CommonInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public OpenAuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            OpenAuth classAnnotation = handlerMethod.getBeanType().getAnnotation(OpenAuth.class);
            OpenAuth methodAnnotation = handlerMethod.getMethodAnnotation(OpenAuth.class);
            if (classAnnotation != null || methodAnnotation != null) {
                String appKey = request.getHeader("appKey");
                String sign = request.getHeader("sign");
                String timestamp = request.getHeader("timestamp");
                if (!isValidParam(appKey, sign, timestamp)) {
                    returnError(response, HttpCode.AUTH_PARAM_INVALID);
                    return false;
                }
                if (!authService.isValid(request)) {
                    log.error("auth failed. appKey:{}, sign:{}", appKey, sign);
                    returnError(response, HttpCode.AUTH_FAILED);
                    return false;
                }
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean isValidParam(String appKey, String sign, String timestamp) {
        if (StringUtils.isBlank(appKey) || StringUtils.isBlank(sign) || StringUtils.isBlank(timestamp) || !StringUtils.isNumeric(timestamp)) {
            return false;
        }
        long requestDate = Long.parseLong(timestamp);
        long currentDate = System.currentTimeMillis();
        if ((currentDate - requestDate) > 5000L) {
            log.error("auth failed. current time is greater than the request time by 5 seconds");
            return false;
        }
        return true;
    }
}
