package com.xiaomi.web.core.exception;


import com.xiaomi.web.core.enumeration.HttpStatus;

/**
 * @author liuchiyun
 */
public class RequestParseException extends ServletException {

    public RequestParseException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public RequestParseException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public RequestParseException(String message, Throwable throwable) {
        super(HttpStatus.BAD_REQUEST, message, throwable);
    }
}
