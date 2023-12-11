package com.xiaomi.demo.http;

import okhttp3.MediaType;

/**
 * @Author: liuchiyun
 * @Date: 2023/10/7
 */

public final class Constants {
    private Constants() {

    }

    public static final MediaType MEDIA_JSON = MediaType.get("application/json; charset=utf-8");
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
}
