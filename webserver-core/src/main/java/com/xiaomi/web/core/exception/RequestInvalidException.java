package com.xiaomi.web.core.exception;


import com.xiaomi.web.core.enumeration.HttpStatus;
import com.xiaomi.web.core.exception.base.ServletException;

/**
 * Created by SinjinSong on 2017/7/21.
 * 请求数据不合法
 */
public class RequestInvalidException extends ServletException {
    private static final HttpStatus status = HttpStatus.BAD_REQUEST;
    public RequestInvalidException() {
        super(status);
    }
}
