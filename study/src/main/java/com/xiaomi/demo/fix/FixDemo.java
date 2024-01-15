package com.xiaomi.demo.fix;

import quickfix.ConfigError;
import quickfix.SessionNotFound;

import java.util.concurrent.CountDownLatch;

public class FixDemo {

    public static void main(String[] args) throws InterruptedException, ConfigError, SessionNotFound {
        startInit();
    }

    public static void startServer() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        FixAcceptor fixAcceptor = new FixAcceptor();
        fixAcceptor.startServer();
        count.await();
    }

    public static void startInit() throws ConfigError, InterruptedException, SessionNotFound {
        CountDownLatch count = new CountDownLatch(1);
        FixInitor fixInitor = new FixInitor();
        fixInitor.start();
        Thread.sleep(3000L);
        fixInitor.sendOrder();
        count.await();
    }


}
