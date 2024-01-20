package com.xiaomi.web.core.network.endpoint.bio;

import com.xiaomi.web.core.network.connector.bio.BioAcceptor;
import com.xiaomi.web.core.network.dispatcher.bio.BioDispatcher;
import com.xiaomi.web.core.network.endpoint.Endpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * BIO网络传输模块的入口
 */
@Slf4j
public class BioEndpoint extends Endpoint {
    private ServerSocket server;
    private BioAcceptor acceptor;
    private BioDispatcher dispatcher;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    public void start(int port) {
        try {
            dispatcher = new BioDispatcher();
            server = new ServerSocket(port);
            isRunning.compareAndSet(false, true);
            initAcceptor();
            log.info("server start at port:{} success", port);
        } catch (Exception e) {
            log.error("server start at port:{} failed", port);
            close();
        }
    }

    private void initAcceptor() {
        acceptor = new BioAcceptor(this, dispatcher);
        Thread t = new Thread(acceptor, "bio-acceptor");
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void close() {
        if (!isRunning()) {
            return;
        }
        dispatcher.shutdown();
        try {
            server.close();
        } catch (Exception ex) {
            log.error("close endpoint failed.", ex);
        }
        isRunning.compareAndSet(true, false);
    }

    public Socket accept() throws IOException {
        return server.accept();
    }

    public boolean isRunning() {
        return isRunning.get();
    }
}
