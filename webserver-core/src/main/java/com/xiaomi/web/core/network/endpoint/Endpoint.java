package com.xiaomi.web.core.network.endpoint;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

@Slf4j
public abstract class Endpoint {
    /**
     * 启动服务器
     *
     * @param port 端口
     */
    public abstract void start(int port);

    /**
     * 关闭服务器
     */
    public abstract void close();

    public static Endpoint getInstance(@Nonnull EndpointType endpointType) {
        try {
            return (Endpoint) endpointType.getClazz().newInstance();
        } catch (Exception ex) {
            log.error("get instance failed. type:{}", endpointType);
            throw new RuntimeException("get instance failed. type:" + endpointType);
        }
    }
}
