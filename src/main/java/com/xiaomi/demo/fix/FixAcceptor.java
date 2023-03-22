package com.xiaomi.demo.fix;

import lombok.extern.slf4j.Slf4j;
import quickfix.*;

@Slf4j
public class FixAcceptor {
    private Application fixApplication;
    private static ThreadedSocketAcceptor acceptor;
    private static SessionSettings settings;
    public FixAcceptor() {
        try {
            settings = new SessionSettings("src/main/resources/quickfix-acceptor.properties");
        } catch (ConfigError configError) {
            log.error("Warning config error",configError);
        }

        fixApplication = new FixAcceptorApplication();
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory(); // 不是quickfix.fix44.MessageFactory
        try {
            acceptor = new ThreadedSocketAcceptor(fixApplication, storeFactory, settings, logFactory, messageFactory);
        } catch (ConfigError configError) {
            System.out.println("Warning: config error! " + configError);
        }
    }

    public void startServer() {
        try {
            acceptor.start();
        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
    }

    public void stopServer() {
        acceptor.stop();
    }
}
