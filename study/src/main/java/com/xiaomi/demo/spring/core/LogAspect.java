package com.xiaomi.demo.spring.core;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/13
 */
@Aspect
@Slf4j
@Component
public class LogAspect {


    @Around("@annotation(logAnnotation)")
    public Object before(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        log.info("before,method:{}", joinPoint.getSignature().getName());
        Object proceed = joinPoint.proceed();
        log.info("after");
        return proceed;
    }
}


