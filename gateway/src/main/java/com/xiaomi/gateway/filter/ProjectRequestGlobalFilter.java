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

package com.xiaomi.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * @author project
 * @date 2018/10/8
 * <p>
 * 全局拦截器，作用所有的微服务
 * <p>
 * 1. 对请求头中参数进行处理 from 参数进行清洗 2. 重写StripPrefix = 1,支持全局
 * <p>
 * 支持swagger添加X-Forwarded-Prefix header （F SR2 已经支持，不需要自己维护）
 */
@Component
@Slf4j
public class ProjectRequestGlobalFilter implements GlobalFilter, Ordered {

    /**
     * Process the Web request and (optionally) delegate to the next {@code WebFilter}
     * through the given {@link GatewayFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 清洗请求头中from 参数
        ServerHttpRequest request = null;
//        exchange.getRequest().mutate().headers(httpHeaders -> {
//            httpHeaders.remove(SecurityConstants.FROM);
//            // 设置请求时间
//            httpHeaders.put(CommonConstants.REQUEST_START_TIME,
//                    Collections.singletonList(String.valueOf(System.currentTimeMillis())));
//        }).build();

        // 2. 重写StripPrefix
        addOriginalRequestUrl(exchange, request.getURI());
        String rawPath = request.getURI().getRawPath();
        String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/")).skip(1L)
                .collect(Collectors.joining("/"));

        // 3. 内部需要转换成http协议
        ServerHttpRequest newRequest = buildNewRequest(request, newPath);
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
        return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
    }

    private ServerHttpRequest buildNewRequest(ServerHttpRequest request, String newPath) {
        //原始uri
        URI originUri = request.getURI();
        //构造器
        ServerHttpRequest.Builder mutate = request.mutate();
        //需要重定向的uri
        String forwardedUri = originUri.toString();
        if (StringUtils.startsWithIgnoreCase(forwardedUri, "https")) {
            try {
                //重新生成http请求方式的uri
                URI uri = new URI(
                        "http",
                        originUri.getUserInfo(),
                        originUri.getHost(),
                        originUri.getPort(),
                        originUri.getPath(),
                        originUri.getQuery(),
                        originUri.getFragment()
                );
                mutate.uri(uri);
            } catch (URISyntaxException e) {
                log.error("build Uri failed.", e);
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
        return mutate.path(newPath).build();
    }

    @Override
    public int getOrder() {
        return 10;
    }

}
