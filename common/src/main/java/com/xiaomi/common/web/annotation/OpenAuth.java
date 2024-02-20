package com.xiaomi.common.web.annotation;

import java.lang.annotation.*;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/1
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenAuth {
}
