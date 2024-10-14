package com.xiaomi.demo.spring.mvc;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: liuchiyun
 * @Date: 2024/10/14
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {PhoneNumberValidator.class})
public @interface PhoneNumber {

    String message() default "手机号格式有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

