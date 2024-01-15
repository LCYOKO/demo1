package com.xiaomi.demo.document.report.word.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author:柳维民
 * @Date: 2023/9/26 14:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DocxPosition {

    /**
     * 行数
     */
    int rowNum() default -1;

    /**
     * 列数
     */
    int column() default -1;


    /**
     * 数据来源
     */
    SourceFromTypeEnum sourceFromType() default SourceFromTypeEnum.SOURCE_FROM_TABLE;


    /**
     * 来源表头名
     */
    String tableName() default "";


    /**
     * 匹配关键字
     */
    String keywords() default "";


    /**
     * 是否需要转换
     */
    boolean needTransform() default false;

    /**
     * 段落位置
     */
    int[] phaseLocations() default {};


    /**
     * 下划线位置
     */
    int[] underLineLocations() default {};


    /**
     * 以数字开头
     */
    boolean startWithNum() default false;

    /**
     * 使用正则匹配
     */
    boolean useRegx() default false;


    /**
     * 返回值转换处理
     */
    TransformEnum transformAction() default TransformEnum.TRANSFORM_DEFAULT;


}
