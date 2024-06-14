package com.xiaomi.demo.spring.core;

import java.lang.annotation.*;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface Log {
}
