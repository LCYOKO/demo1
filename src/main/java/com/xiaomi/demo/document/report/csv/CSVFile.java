package com.xiaomi.demo.document.report.csv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author liuchiyun
 * @Date 2023/7/21 3:01 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CSVFile {
    int appendBlankSize() default 0;
}
