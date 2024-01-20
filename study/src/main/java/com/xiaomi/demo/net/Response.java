package com.xiaomi.demo.net;

import com.xiaomi.web.core.cookie.Cookie;
import com.xiaomi.web.core.enumeration.HttpStatus;
import com.xiaomi.web.core.response.Header;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
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
    private final StringBuilder headerAppender;
    private final List<Cookie> cookies;
    private final List<Header> headers;
    private HttpStatus status = HttpStatus.OK;
    private String contentType = DEFAULT_CONTENT_TYPE;
    private byte[] body = new byte[0];
    private com.xiaomi.web.core.network.handler.AbstractRequestHandler requestHandler;

    public Response() {
        this.headerAppender = new StringBuilder();
        this.cookies = new ArrayList<>();
        this.headers = new ArrayList<>();
    }

    /**
     * 设置HTTP Status
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }


    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void addHeader(Header header) {
        headers.add(header);
    }


    private void buildHeader() {
        //HTTP/1.1 200 OK
        headerAppender.append("HTTP/1.1").append(BLANK).append(status.getCode()).append(BLANK).append(status).append(CRLF);
        //Date: Sat, 31 Dec 2005 23:59:59 GMT
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
        headerAppender.append("Content-Length:").append(BLANK);
    }

    //一次性传入响应体
    private void buildBody() {
        this.headerAppender.append(body.length).append(CRLF).append(CRLF);
    }

    /**
     * response构建的最后一步，将header和body转为字节数组
     */
    private void buildResponse() {
        buildHeader();
        buildBody();
    }

    /**
     * 返回Response构建后的数据，用于NIO/AIO
     */
    public ByteBuffer[] getResponseByteBuffer() {
        buildResponse();
        byte[] header = this.headerAppender.toString().getBytes(UTF_8_CHARSET);
        return new ByteBuffer[]{ByteBuffer.wrap(header), ByteBuffer.wrap(body)};
    }

    /**
     * 返回Response构建后的数据，用于BIO
     */
    public byte[] getResponseBytes() {
        buildResponse();
        byte[] header = this.headerAppender.toString().getBytes(UTF_8_CHARSET);
        byte[] response = new byte[header.length + body.length];
        System.arraycopy(header, 0, response, 0, header.length);
        System.arraycopy(body, 0, response, header.length, body.length);
        return response;
    }

//    /**
//     * 重定向，注意重定向后会立即写数据至socket中
//     */
//    public void sendRedirect(String url) {
//        log.info("重定向至{}", url);
//        addHeader(new Header("Location", url));
//        setStatus(HttpStatus.MOVED_TEMPORARILY);
//        buildResponse();
//        // 刷新至客户端
//        requestHandler.flushResponse();
//    }
//
//    /**
//     * 用于调用不同RequestHandler的写刷新（将response写入到客户端）
//     */
//    public void setRequestHandler(AbstractRequestHandler requestHandler) {
//        this.requestHandler = requestHandler;
//    }
}
