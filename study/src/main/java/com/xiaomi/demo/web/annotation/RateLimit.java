package com.xiaomi.demo.web.annotation;

import java.lang.annotation.*;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/1
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    /**
     * 默认全局限流
     *
     * @return 限流类型
     */
    String type() default "global";

    /**
     * 默认qps 30/s
     *
     * @return qps
     */
    double permitsPerSecond() default 30D;

}
