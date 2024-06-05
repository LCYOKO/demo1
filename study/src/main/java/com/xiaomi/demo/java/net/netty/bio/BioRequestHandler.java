package com.xiaomi.demo.java.net.netty.bio;

import com.xiaomi.demo.java.net.AbstractRequestHandler;
import com.xiaomi.demo.java.net.Request;
import com.xiaomi.demo.java.net.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
@Slf4j
public class BioRequestHandler extends AbstractRequestHandler<BioSocketWrapper> implements Runnable {
    public BioRequestHandler(BioSocketWrapper socketWrapper, Request request, Response response) {
        super(socketWrapper, request, response);
    }

    @Override
    public void service() {
        log.info("request:{}", request);
        try (OutputStream outputStream = socketWrapper.getSocket().getOutputStream()) {
            response.setBody("hello world".getBytes(StandardCharsets.UTF_8));
            outputStream.write(response.getResponseBytes());
            outputStream.flush();
        } catch (IOException ex) {
            log.error("do service failed.", ex);
        }

    }

    @Override
    public void run() {
        try {
            service();
        } catch (Exception ex) {
            log.error("do service failed.", ex);
        }
    }
}
