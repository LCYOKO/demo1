package com.xiaomi.common.web.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 * 参数错误异常
 *
 * @author yhdja
 * @version [1.1.0, 2023/6/5]
 **/
public class InvalidParamException extends RuntimeException {

    public InvalidParamException() {
        super();
    }

    public InvalidParamException(String message) {
        super(message);
    }

    public InvalidParamException(String formatPattern, Object... args) {
        super(MessageFormatter.arrayFormat(formatPattern, args).getMessage());
    }

}
