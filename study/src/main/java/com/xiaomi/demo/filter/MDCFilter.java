package com.xiaomi.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/15
 */
@Slf4j
public class MDCFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String flag = request.getHeader("flag");
        if (StringUtils.isNotBlank(flag)) {
            MDC.put("dunshan", "shadow");
            log.info("flag：" + flag);
        } else {
            MDC.put("dunshan", "produce");
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        MDC.clear();
    }
}
