package com.xiaomi.web.core.network.handler.nio;

import com.xiaomi.web.core.context.ServletContext;
import com.xiaomi.web.core.context.WebApplication;
import com.xiaomi.web.core.exception.FilterNotFoundException;
import com.xiaomi.web.core.exception.ServletNotFoundException;
import com.xiaomi.web.core.exception.handler.ExceptionHandler;
import com.xiaomi.web.core.network.handler.AbstractRequestHandler;
import com.xiaomi.web.core.network.wrapper.SocketWrapper;
import com.xiaomi.web.core.network.wrapper.nio.NioSocketWrapper;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.resource.ResourceHandler;
import com.xiaomi.web.core.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by SinjinSong on 2017/7/21.
 */
@Setter
@Getter
@Slf4j
public class NioRequestHandler extends AbstractRequestHandler {

    public NioRequestHandler(SocketWrapper socketWrapper, ServletContext servletContext, ExceptionHandler exceptionHandler, ResourceHandler resourceHandler, Request request, Response response) throws ServletNotFoundException, FilterNotFoundException {
        super(socketWrapper, servletContext, exceptionHandler, resourceHandler, request, response);
    }

    /**
     * 写入后会根据请求头Connection来判断是关闭连接还是重新将连接放回Poller，实现保活
     */
    @Override
    public void flushResponse() {
        isFinished = true;
        NioSocketWrapper nioSocketWrapper = (NioSocketWrapper) socketWrapper;
        ByteBuffer[] responseData = response.getResponseByteBuffer();
        try {
            nioSocketWrapper.getSocketChannel().write(responseData);
            List<String> connection = request.getHeaders().get("Connection");
            if (connection != null && connection.get(0).equals("close")) {
                log.info("CLOSE: 客户端连接{} 已关闭", nioSocketWrapper.getSocketChannel());
                nioSocketWrapper.close();
            } else {
                // keep-alive 重新注册到Poller中
                log.info("KEEP-ALIVE: 客户端连接{} 重新注册到Poller中", nioSocketWrapper.getSocketChannel());
                nioSocketWrapper.getNioPoller().register(nioSocketWrapper.getSocketChannel(), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebApplication.getServletContext().afterRequestDestroyed(request);
    }
}
