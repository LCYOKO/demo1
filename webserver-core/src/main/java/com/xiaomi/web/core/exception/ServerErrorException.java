package com.xiaomi.web.core.exception;


import com.xiaomi.web.core.enumeration.HttpStatus;
import com.xiaomi.web.core.exception.base.ServletException;

/**
 * Created by SinjinSong on 2017/7/21.
 * 500异常
 */
public class ServerErrorException extends ServletException {
    private static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    public ServerErrorException() {
        super(status);
    }
}
