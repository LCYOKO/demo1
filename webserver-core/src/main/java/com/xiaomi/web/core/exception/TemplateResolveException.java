package com.xiaomi.web.core.exception;

import com.xiaomi.web.core.enumeration.HttpStatus;

/**
 * Created by SinjinSong on 2017/7/21.
 * 模板引擎解析错误（html文件编写错误）
 */
public class TemplateResolveException extends ServletException {

    public TemplateResolveException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public TemplateResolveException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public TemplateResolveException(String message, Throwable throwable) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, throwable);
    }
}
   
