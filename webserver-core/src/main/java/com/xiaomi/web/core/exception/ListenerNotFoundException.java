package com.xiaomi.web.core.exception;

import com.xiaomi.web.core.enumeration.HttpStatus;
import com.xiaomi.web.core.exception.base.ServletException;

/**
 * @author sinjinsong
 * @date 2018/5/3
 * 未找到对应的Listener（web.xml配置错误）
 */
public class ListenerNotFoundException extends ServletException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;

    public ListenerNotFoundException() {
        super(status);
    }
}