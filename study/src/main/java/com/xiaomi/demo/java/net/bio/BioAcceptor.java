package com.xiaomi.demo.java.net.bio;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
@Slf4j
public class BioAcceptor implements Runnable {
    private final BioEndpoint bioEndpoint;
    private final BioDispatcher dispatcher;

    public BioAcceptor(BioEndpoint bioEndpoint) {
        this.bioEndpoint = bioEndpoint;
        this.dispatcher = new BioDispatcher();
    }

    @Override
    public void run() {
        while (bioEndpoint.isRunning()) {
            try {
                final Socket clientSocket = bioEndpoint.accept();
                dispatcher.doDispatch(new BioSocketWrapper(clientSocket));
            } catch (Exception e) {
                log.error("accept failed.", e);
            }
        }
    }

    protected void shutdown() {
        dispatcher.shutdown();
    }
}
