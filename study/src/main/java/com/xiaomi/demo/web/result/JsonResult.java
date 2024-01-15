package com.xiaomi.demo.web.result;

import com.xiaomi.demo.web.constants.HttpCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/1
 */
@Data
@AllArgsConstructor
public class JsonResult<E> {
    private int code;
    private String message;
    private E data;
    private long timestamp;

    public static <T> JsonResult<T> ok() {
        return new JsonResult<>(HttpCode.OK.getCode(), HttpCode.OK.getMsg(), null, System.currentTimeMillis());
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<>(HttpCode.OK.getCode(), HttpCode.OK.getMsg(), data, System.currentTimeMillis());
    }

    public static <T> JsonResult<T> notFound() {
        return new JsonResult<>(HttpCode.NOT_FOUND.getCode(), HttpCode.NOT_FOUND.getMsg(), null, System.currentTimeMillis());
    }

    public static <T> JsonResult<T> notFound(T data) {
        return new JsonResult<>(HttpCode.NOT_FOUND.getCode(), HttpCode.NOT_FOUND.getMsg(), data, System.currentTimeMillis());
    }

    public static <T> JsonResult<T> error() {
        return new JsonResult<>(HttpCode.ERROR.getCode(), HttpCode.ERROR.getMsg(), null, System.currentTimeMillis());
    }

    public static <T> JsonResult<T> error(T data) {
        return new JsonResult<>(HttpCode.ERROR.getCode(), HttpCode.ERROR.getMsg(), data, System.currentTimeMillis());
    }

    public static <T> JsonResult<T> error(HttpCode code) {
        return new JsonResult<>(code.getCode(), code.getMsg(), null, System.currentTimeMillis());
    }

    public static <T> JsonResult<T> error(HttpCode code, T data) {
        return new JsonResult<>(code.getCode(), code.getMsg(), data, System.currentTimeMillis());
    }
}
