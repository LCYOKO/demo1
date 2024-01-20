/*
 *    Copyright (c) 2018-2025, project All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: project
 */

package com.xiaomi.gateway.configuration;

import com.xiaomi.gateway.support.DynamicRouteHealthIndicator;
import com.xiaomi.gateway.support.RouteCacheHolder;
import com.xiaomi.gateway.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author project
 * @date 2018/11/5
 * <p>
 * 动态路由配置类
 */
@Slf4j
@Configuration
@ComponentScan("com.xiaomi.gateway")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class DynamicRouteAutoConfiguration {
    /**
     * redis 监听配置
     *
     * @param redisConnectionFactory redis 配置
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener((message, bytes) -> {
            log.warn("接收到重新JVM 重新加载路由事件");
            RouteCacheHolder.removeRouteList();
            // 发送刷新路由事件
//			SpringContextHolder.publishEvent(new RefreshRoutesEvent(this));
        }, new ChannelTopic(Constants.ROUTE_JVM_RELOAD_TOPIC));
        return container;
    }

    /**
     * 动态路由监控检查
     *
     * @param redisTemplate redis
     * @return
     */
    @Bean
    public DynamicRouteHealthIndicator healthIndicator(RedisTemplate redisTemplate) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        if (!redisTemplate.hasKey(Constants.ROUTE_KEY)) {
            log.info("路由信息未初始化，网关路由失败");
        }
        return new DynamicRouteHealthIndicator(redisTemplate);
    }

}
