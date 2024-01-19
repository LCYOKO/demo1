package com.xiaomi.web.filter;

import com.xiaomi.web.core.filter.Filter;
import com.xiaomi.web.core.filter.FilterChain;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sinjinsong
 * @date 2018/5/3
 */
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init() {
        log.info("LogFilter init...");
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        log.info("{} before accessed, method is {}", request.getUrl(), request.getMethod());
        filterChain.doFilter(request, response);
        log.info("{} after accessed, method is {}", request.getUrl(), request.getMethod());
    }

    @Override
    public void destroy() {
        log.info("LogFilter destroy...");
    }
}
