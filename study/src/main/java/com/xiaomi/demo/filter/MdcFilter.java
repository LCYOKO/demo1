package com.xiaomi.demo.filter;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author liuchiyun
 * @Date 2024/7/15
 */
@Slf4j
public class MdcFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        UUID uuid = UUID.fastUUID();
        MDC.put("traceId", uuid.toString());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        MDC.clear();
    }
}

