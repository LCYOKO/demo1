package com.xiaomi.common.web;

import lombok.Getter;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/16
 */
public enum HttpCode {
    /**
     * HTTP 原生状态码
     */
    OK(200, "请求成功"),

    ERROR(500, "服务器内部错误");

    /**
     * HTTP 业务码
     */
    @Getter
    private final int code;
    @Getter
    private final String msg;

    HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
