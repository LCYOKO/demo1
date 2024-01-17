package com.xiaomi.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/16
 */
@Data
@AllArgsConstructor
public class JsonResult<T> {
    private int code;
    private String msg;
    private T data;
    private long timestamp = System.currentTimeMillis();

    public JsonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResult<Void> ok() {
        return new JsonResult<>(HttpCode.OK.getCode(), HttpCode.OK.getMsg(), null);
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<>(HttpCode.OK.getCode(), HttpCode.OK.getMsg(), data);

    }


}
