package com.xiaomi.web.core.exception;

import com.xiaomi.web.core.enumeration.HttpStatus;
import lombok.Getter;

/**
 * 根异常
 */
@Getter
public class ServletException extends Exception {
    private final HttpStatus httpStatus;

    public ServletException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ServletException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ServletException(HttpStatus httpStatus, String message, Throwable ex) {
        super(message, ex);
        this.httpStatus = httpStatus;
    }
}
