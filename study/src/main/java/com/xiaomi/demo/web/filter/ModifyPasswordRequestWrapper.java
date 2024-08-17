package com.xiaomi.demo.web.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/9
 */
public class ModifyPasswordRequestWrapper extends HttpServletRequestWrapper {
    private final String modifyBody;
    private final Map<String, String> paramMap;

    public ModifyPasswordRequestWrapper(HttpServletRequest request, String modifyBody, Map<String, String> paramMap) {
        super(request);
        this.modifyBody = modifyBody;
        this.paramMap = paramMap;
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(modifyBody.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public String getParameter(String name) {
        return paramMap.get(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[]{paramMap.get(name)};
    }
}
