package com.xiaomi.demo.java.net.netty.mqtt.common.auth;

/**
 * 用户和密码认证服务接口
 */
public interface GrozaAuthService {
    /**
     * 验证用户名和密码是否正确
     */
    boolean checkValid(String username, String password);
}
