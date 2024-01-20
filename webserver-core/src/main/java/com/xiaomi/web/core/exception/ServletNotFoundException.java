package com.xiaomi.web.core.exception;

import com.xiaomi.web.core.enumeration.HttpStatus;

/**
 * @Author：liuchiyun 未找到对应的Servlet（web.xml配置错误）
 */
public class ServletNotFoundException extends ServletException {

    public ServletNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public ServletNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public ServletNotFoundException(String message, Throwable throwable) {
        super(HttpStatus.NOT_FOUND, message, throwable);
    }
}
