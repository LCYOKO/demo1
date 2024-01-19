package com.xiaomi.web.core.exception;

import com.xiaomi.web.core.enumeration.HttpStatus;
import com.xiaomi.web.core.exception.base.ServletException;

/**
 * Created by SinjinSong on 2017/7/21.
 * 模板引擎解析错误（html文件编写错误）
 */
public class TemplateResolveException extends ServletException {
    private static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public TemplateResolveException() {
        super(status);
    }
}
   
