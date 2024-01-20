package com.xiaomi.web.core.exception;


import com.xiaomi.web.core.enumeration.HttpStatus;

/**
 * @author liuchiyun
 * 请求数据不合法
 */
public class RequestInvalidException extends ServletException {

    public RequestInvalidException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public RequestInvalidException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public RequestInvalidException(String message, Throwable throwable) {
        super(HttpStatus.BAD_REQUEST, message, throwable);
    }
}
