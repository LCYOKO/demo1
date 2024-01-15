package com.xiaomi.demo.web.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import com.xiaomi.demo.web.annotation.RateLimit;
import com.xiaomi.demo.web.constants.HttpCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/1
 */
@Slf4j
public class RateLimiterInterceptor extends CommonInterceptor implements HandlerInterceptor {

    private final Map<String, RateLimiter> TYPE_2_RATE_LIMITER = new ConcurrentHashMap<>(16);

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RateLimit classAnnotation = handlerMethod.getBeanType().getAnnotation(RateLimit.class);
            RateLimit methodAnnotation = handlerMethod.getMethodAnnotation(RateLimit.class);
            if (classAnnotation != null || methodAnnotation != null) {
                if (!shouldApproval(classAnnotation == null ? methodAnnotation : classAnnotation)) {
                    log.error("req exceed limit");
                    returnError(response, HttpCode.RATE_LIMIT);
                    return false;
                }
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean shouldApproval(RateLimit rateLimit) {
        String type = rateLimit.type();
        double permitsPerSecond = rateLimit.permitsPerSecond();
        TYPE_2_RATE_LIMITER.putIfAbsent(type, RateLimiter.create(permitsPerSecond));
        return TYPE_2_RATE_LIMITER.get(type).tryAcquire();
    }
}
