package com.xiaomi.demo.net;

import com.xiaomi.web.core.constant.CharConstant;
import com.xiaomi.web.core.constant.CharsetProperties;
import com.xiaomi.web.core.enumeration.RequestMethod;
import com.xiaomi.web.core.exception.RequestInvalidException;
import com.xiaomi.web.core.exception.RequestParseException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
@Slf4j
@Data
public class Request {
    private RequestMethod method;
    private String url;
    private Map<String, List<String>> params;
    private Map<String, List<String>> headers;

    /**
     * 获取queryString或者body（表单格式）的键值类型的数据
     */
    public String getParameter(String key) {
        List<String> params = this.params.get(key);
        if (CollectionUtils.isEmpty(params)) {
            return null;
        }
        return params.get(0);
    }

    /**
     * 解析HTTP请求
     * 读取请求体只能使用字节流，使用字符流读不到
     */
    public Request(byte[] data) throws RequestParseException, RequestInvalidException, IOException {
        String[] lines;
        try {
            //支持中文，对中文进行URL解码
            lines = URLDecoder.decode(new String(data, CharsetProperties.UTF_8_CHARSET), CharsetProperties.UTF_8).split(CharConstant.CRLF);
        } catch (Exception e) {
            log.error("decode failed.", e);
            throw new RuntimeException("decode failed", e);
        }
        if (lines.length <= 1) {
            log.error("lines:{}", Arrays.toString(lines));
            throw new RequestInvalidException();
        }
        try {
            parseHeaders(lines);
            if (headers.containsKey("Content-Length") && !"0".equals(headers.get("Content-Length").get(0))) {
                parseBody(lines[lines.length - 1]);
            }
        } catch (Exception ex) {
            log.error("parse lines:{} failed.", Arrays.toString(lines));
            throw new RequestParseException();
        }
    }

    public String getServletPath() {
        return url;
    }

    public String getMethod() {
        return method.toString();
    }

    private void parseHeaders(String[] lines) {
        String firstLine = lines[0];
        //解析方法
        String[] firstLineSlices = firstLine.split(CharConstant.BLANK);
        this.method = RequestMethod.valueOf(firstLineSlices[0]);
        //解析URL
        String rawUrl = firstLineSlices[1];
        String[] urlSlices = rawUrl.split("\\?");
        this.url = urlSlices[0];
        //解析URL参数
        if (urlSlices.length > 1) {
            parseParams(urlSlices[1]);
        }
        //解析请求头
        String header;
        this.headers = new HashMap<>(16);
        for (int i = 1; i < lines.length; i++) {
            header = lines[i];
            if ("".equals(header)) {
                break;
            }
            int colonIndex = header.indexOf(':');
            String key = header.substring(0, colonIndex);
            String[] values = header.substring(colonIndex + 2).split(",");
            headers.put(key, Arrays.asList(values));
        }
    }

    private void parseBody(String body) {
        byte[] bytes = body.getBytes(CharsetProperties.UTF_8_CHARSET);
        List<String> lengths = this.headers.get("Content-Length");
        if (lengths != null) {
            int length = Integer.parseInt(lengths.get(0));
            log.info("length:{}", length);
            parseParams(new String(bytes, 0, Math.min(length, bytes.length), CharsetProperties.UTF_8_CHARSET).trim());
        } else {
            parseParams(body.trim());
        }
        if (this.params == null) {
            this.params = new HashMap<>();
        }
    }

    private void parseParams(String params) {
        String[] urlParams = params.split("&");
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        for (String param : urlParams) {
            String[] kv = param.split("=");
            String key = kv[0];
            String[] values = kv[1].split(",");
            this.params.put(key, Arrays.asList(values));
        }
    }
}
