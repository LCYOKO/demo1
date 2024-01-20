package com.xiaomi.web.core.exception;

import com.xiaomi.web.core.enumeration.HttpStatus;

/**
 * @author liuchiyun
 * 未找到对应的Listener（web.xml配置错误）
 */
public class ListenerNotFoundException extends ServletException {

    public ListenerNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public ListenerNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public ListenerNotFoundException(String message, Throwable throwable) {
        super(HttpStatus.NOT_FOUND, message, throwable);
    }
}