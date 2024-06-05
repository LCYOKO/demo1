package com.xiaomi.demo.java.net.netty.bio;

import com.xiaomi.demo.java.net.Endpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
@Slf4j
public class BioEndpoint implements Endpoint {
    private ServerSocket server;
    private final int port;
    private final BioAcceptor acceptor;
    private final ExecutorService service;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public BioEndpoint(int port) {
        this.port = port;
        this.acceptor = new BioAcceptor(this);
        this.service = Executors.newSingleThreadExecutor();
    }

    @Override
    public boolean start() {
        try {
            server = new ServerSocket(port);
            isRunning.compareAndSet(false, true);
            service.execute(acceptor);
            log.info("start server at port:{} success.", port);
            return true;
        } catch (Exception ex) {
            log.error("start server at port:{} failed.", port, ex);
        }
        return false;
    }

    @Override
    public boolean close() {
        try {
            server.close();
            acceptor.shutdown();
            isRunning.compareAndSet(true, false);
            return true;
        } catch (Exception ex) {
            log.error("close server failed.", ex);
        }
        return false;
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    protected Socket accept() throws IOException {
        return server.accept();
    }
}
