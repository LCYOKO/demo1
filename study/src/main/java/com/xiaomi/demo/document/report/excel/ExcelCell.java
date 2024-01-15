package com.xiaomi.demo.document.report.excel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author liuchiyun
 * @Date 2023/7/21 3:24 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCell {
    @AliasFor("value")
    String header() default "";

    @AliasFor("header")
    String value() default "";

    /**
     * @deprecated
     */
    @Deprecated
    String format() default "";

    /**
     * Date, LocalDate等类型字段的格式
     * 多种格式任意一种匹配即可
     */
    String[] formats() default {};

    String defaultValue() default StringUtils.EMPTY;

    boolean required() default true;
}
