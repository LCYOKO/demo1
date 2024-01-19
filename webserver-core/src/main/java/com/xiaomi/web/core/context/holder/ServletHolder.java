package com.xiaomi.web.core.context.holder;

import com.xiaomi.web.core.servlet.Servlet;
import lombok.Data;

/**
 * @author sinjinsong
 * @date 2018/5/2
 */
@Data
public class ServletHolder {
    private Servlet servlet;
    private String servletClass;

    public ServletHolder(String servletClass) {
        this.servletClass = servletClass;
    }
}
