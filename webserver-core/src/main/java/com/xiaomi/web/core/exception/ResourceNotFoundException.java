package com.xiaomi.web.core.exception;

import com.xiaomi.web.core.enumeration.HttpStatus;

/**
 * @Author：liuchiyun
 */
public class ResourceNotFoundException extends ServletException {

    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public ResourceNotFoundException(String message, Throwable throwable) {
        super(HttpStatus.NOT_FOUND, message, throwable);
    }
}
