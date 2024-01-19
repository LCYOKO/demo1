package com.xiaomi.web.core.network.handler.bio;


import com.xiaomi.web.core.context.ServletContext;
import com.xiaomi.web.core.context.WebApplication;
import com.xiaomi.web.core.exception.FilterNotFoundException;
import com.xiaomi.web.core.exception.ServletNotFoundException;
import com.xiaomi.web.core.exception.handler.ExceptionHandler;
import com.xiaomi.web.core.network.handler.AbstractRequestHandler;
import com.xiaomi.web.core.network.wrapper.SocketWrapper;
import com.xiaomi.web.core.network.wrapper.bio.BioSocketWrapper;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.resource.ResourceHandler;
import com.xiaomi.web.core.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by SinjinSong on 2017/7/21.
 * Servlet运行容器
 */
@Setter
@Getter
@Slf4j
public class BioRequestHandler extends AbstractRequestHandler {

    public BioRequestHandler(SocketWrapper socketWrapper, ServletContext servletContext, ExceptionHandler exceptionHandler, ResourceHandler resourceHandler, Request request, Response response) throws ServletNotFoundException, FilterNotFoundException {
        super(socketWrapper, servletContext, exceptionHandler, resourceHandler, request, response);
    }

    /**
     * 写回后立即关闭socket
     */
    @Override
    public void flushResponse() {
        isFinished = true;
        BioSocketWrapper bioSocketWrapper = (BioSocketWrapper) socketWrapper;
        byte[] bytes = response.getResponseBytes();
        OutputStream os = null;
        try {
            os = bioSocketWrapper.getSocket().getOutputStream();
            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("socket closed");
        } finally {
            try {
                os.close();
                bioSocketWrapper.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        WebApplication.getServletContext().afterRequestDestroyed(request);
    }
}
