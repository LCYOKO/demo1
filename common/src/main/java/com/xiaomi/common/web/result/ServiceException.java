package com.xiaomi.common.web.result;

import com.xiaomi.common.web.constants.HttpCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -6456462121995756702L;

    private HttpCode httpCode;

    private Integer code;
    private Object[] args;

    public ServiceException() {
        super();
    }


    public ServiceException(HttpCode httpCode, String message, Object[] args) {
        super(message);
        this.httpCode = httpCode;
        code = httpCode.getCode();
        this.args = args;
    }

    public ServiceException(HttpCode httpCode) {
        super(httpCode.getMsg());
        this.httpCode = httpCode;
        code = httpCode.getCode();
    }

}
