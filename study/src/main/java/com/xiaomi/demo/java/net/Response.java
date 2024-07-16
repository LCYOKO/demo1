package com.xiaomi.demo.java.net;

import com.xiaomi.web.core.cookie.Cookie;
import com.xiaomi.web.core.enumeration.HttpStatus;
import com.xiaomi.web.core.response.Header;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xiaomi.web.core.constant.CharConstant.BLANK;
import static com.xiaomi.web.core.constant.CharConstant.CRLF;
import static com.xiaomi.web.core.constant.CharsetProperties.UTF_8_CHARSET;
import static com.xiaomi.web.core.constant.ContextConstant.DEFAULT_CONTENT_TYPE;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
@Slf4j
public class Response {
    private final StringBuilder rowAppender;
    private final StringBuilder headerAppender;
    private final List<Cookie> cookies;
    private final List<Header> headers;
    private HttpStatus status = HttpStatus.OK;
    private String contentType = DEFAULT_CONTENT_TYPE;
    private byte[] body = new byte[0];

    public Response() {
        this.rowAppender = new StringBuilder();
        this.headerAppender = new StringBuilder();
        this.cookies = new ArrayList<>();
        this.headers = new ArrayList<>();
    }

    public Response withStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public Response withContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Response withBody(byte[] body) {
        this.body = body;
        return this;
    }

    public Response addCookie(Cookie cookie) {
        cookies.add(cookie);
        return this;
    }

    public Response addHeader(Header header) {
        headers.add(header);
        return this;
    }


    private void buildRow() {
        //HTTP/1.1 200 OK
        rowAppender.append(Constants.HTTP_VERSION).append(BLANK).append(status.getCode()).append(BLANK).append(status).append(CRLF);
    }

    private void buildHeader() {
        headerAppender.append("Date:").append(BLANK).append(new Date()).append(CRLF);
        headerAppender.append("Content-Type:").append(BLANK).append(contentType).append(CRLF);
        for (Header header : headers) {
            headerAppender.append(header.getKey()).append(":").append(BLANK).append(header.getValue()).append(CRLF);
        }
        if (cookies.size() > 0) {
            for (Cookie cookie : cookies) {
                headerAppender.append("Set-Cookie:").append(BLANK).append(cookie.getKey()).append("=").append(cookie.getValue()).append(CRLF);
            }
        }
        headerAppender.append("Content-Length:").append(BLANK).append(body.length).append(CRLF).append(CRLF);
    }

    /**
     * response构建的最后一步，将header和body转为字节数组
     */
    private void buildResponse() {
        buildRow();
        buildHeader();
    }

    /**
     * 返回Response构建后的数据，用于NIO/AIO
     */
    public ByteBuffer getResponseByteBuffer() {
        buildResponse();
        byte[] row = this.rowAppender.toString().getBytes(StandardCharsets.UTF_8);
        byte[] header = this.headerAppender.toString().getBytes(UTF_8_CHARSET);
        return ByteBuffer.wrap(ArrayUtils.addAll(ArrayUtils.addAll(row, header), body));
    }

    /**
     * 返回Response构建后的数据，用于BIO
     */
    public byte[] getResponseBytes() {
        buildResponse();
        byte[] row = this.rowAppender.toString().getBytes(StandardCharsets.UTF_8);
        byte[] header = this.headerAppender.toString().getBytes(UTF_8_CHARSET);
        return ArrayUtils.addAll(ArrayUtils.addAll(row, header), body);
    }
}
