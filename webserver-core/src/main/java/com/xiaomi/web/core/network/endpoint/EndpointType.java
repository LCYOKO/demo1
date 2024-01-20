package com.xiaomi.web.core.network.endpoint;

import com.google.common.collect.ImmutableMap;
import com.xiaomi.web.core.network.endpoint.aio.AioEndpoint;
import com.xiaomi.web.core.network.endpoint.bio.BioEndpoint;
import com.xiaomi.web.core.network.endpoint.nio.NioEndpoint;
import lombok.Getter;

import java.util.Map;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
public enum EndpointType {
    /**
     * bio
     */
    BIO("bio", BioEndpoint.class),
    /**
     * nio
     */
    NIO("nio", NioEndpoint.class),
    /**
     * aio
     */
    AIO("aio", AioEndpoint.class);

    @Getter
    private String type;
    @Getter
    private Class<?> clazz;

    private static final Map<String, EndpointType> TYPE_TO_ENUM = ImmutableMap.of(BIO.type, BIO, NIO.type, NIO, AIO.type, AIO);

    EndpointType(String type, Class<?> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public static EndpointType getOrThrow(String type) {
        EndpointType endpointType = TYPE_TO_ENUM.get(type);
        if (endpointType == null) {
            throw new IllegalArgumentException("invalid type: " + type);
        }
        return endpointType;
    }
}
