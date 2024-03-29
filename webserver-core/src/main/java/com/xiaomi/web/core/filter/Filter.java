package com.xiaomi.web.core.filter;

import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;

/**
 * @author sinjinsong
 * @date 2018/5/2
 * 过滤器
 */
public interface Filter {
    /**
     * 过滤器初始化
     */
    void init();

    /**
     * 过滤
     *
     * @param request
     * @param response
     * @param filterChain
     */
    void doFilter(Request request, Response response, FilterChain filterChain);

    /**
     * 过滤器销毁
     */
    void destroy();
}
