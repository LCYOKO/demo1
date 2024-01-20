package com.xiaomi.web.core.exception;


import com.xiaomi.web.core.enumeration.HttpStatus;

/**
 * @author liuchiyun
 * 未找到对应的Filter（web.xml配置错误）
 */
public class FilterNotFoundException extends ServletException {
    public FilterNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public FilterNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public FilterNotFoundException(String message, Throwable throwable) {
        super(HttpStatus.NOT_FOUND, message, throwable);
    }
}
