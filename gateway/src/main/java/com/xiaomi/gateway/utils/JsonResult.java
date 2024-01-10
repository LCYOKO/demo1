package com.xiaomi.gateway.utils;

import lombok.Data;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/10
 */
@Data
public class JsonResult<T> {
    private int code;
    private T data;
    private String msg;

    public static JsonResult failed(String message) {
        JsonResult<Object> jsonResult = new JsonResult<>();
        jsonResult.setMsg(message);
        return jsonResult;
    }
}
