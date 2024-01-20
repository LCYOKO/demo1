package com.xiaomi.web.core.exception;


import com.xiaomi.web.core.enumeration.HttpStatus;

/**
 * @Author：liuchiyun 500异常
 */
public class ServerErrorException extends ServletException {

    public ServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public ServerErrorException(String message, Throwable throwable) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, throwable);
    }
}
