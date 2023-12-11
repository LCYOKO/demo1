package com.xiaomi.demo.utils;

import org.slf4j.helpers.MessageFormatter;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/11
 */
public class TextUtils {
    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public static String format(String msg, Object... args) {
        return MessageFormatter.arrayFormat(msg, args).getMessage();
    }
}
