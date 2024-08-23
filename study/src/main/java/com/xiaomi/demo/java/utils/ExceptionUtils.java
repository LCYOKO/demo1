package com.xiaomi.demo.java.utils;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/20
 */
public class ExceptionUtils {
    /**
     * 提取真正的异常
     */
    public static Throwable extractRealException(Throwable throwable) {
        if (throwable instanceof CompletionException || throwable instanceof ExecutionException) {
            if (throwable.getCause() != null) {
                return throwable.getCause();
            }
        }
        return throwable;
    }
}
