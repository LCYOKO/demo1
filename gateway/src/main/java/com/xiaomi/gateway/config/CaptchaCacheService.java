package com.xiaomi.gateway.config;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/26
 */
public interface CaptchaCacheService {
    boolean exists(String key);

    void delete(String key);

    String get(String key);

    String type();

    void set(String key, String value, long expiresInSeconds);
}
