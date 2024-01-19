package com.xiaomi.web.core.network.dispatcher.bio;

import com.xiaomi.web.core.exception.RequestInvalidException;
import com.xiaomi.web.core.exception.base.ServletException;
import com.xiaomi.web.core.network.dispatcher.AbstractDispatcher;
import com.xiaomi.web.core.network.handler.bio.BioRequestHandler;
import com.xiaomi.web.core.network.wrapper.SocketWrapper;
import com.xiaomi.web.core.network.wrapper.bio.BioSocketWrapper;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author sinjinsong
 * @date 2018/5/4
 */
@Slf4j
public class BioDispatcher extends AbstractDispatcher {

    @Override
    public void doDispatch(SocketWrapper socketWrapper) {
        BioSocketWrapper bioSocketWrapper = (BioSocketWrapper) socketWrapper;
        Socket socket = bioSocketWrapper.getSocket();
        Request request;
        Response response = null;
        try {
            BufferedInputStream bin = new BufferedInputStream(socket.getInputStream());
            byte[] buf = null;
            try {
                buf = new byte[bin.available()];
                int len = bin.read(buf);
                if (len <= 0) {
                    throw new RequestInvalidException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 这里这里不要把in关掉，把in关掉就等同于把socket关掉
            //解析请求
            response = new Response();
            request = new Request(buf);
            pool.execute(new BioRequestHandler(socketWrapper, servletContext, exceptionHandler, resourceHandler, request, response));
        } catch (ServletException e) {
            exceptionHandler.handle(e, response, socketWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
