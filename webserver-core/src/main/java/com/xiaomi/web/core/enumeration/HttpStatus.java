package com.xiaomi.web.core.enumeration;

import lombok.Getter;

/**
 * @Author：liuchiyun
 */
public enum HttpStatus {
    OK(200),
    MOVED_TEMPORARILY(302),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);
    @Getter
    private int code;

    HttpStatus(int code) {
        this.code = code;
    }
}
