package com.xiaomi.demo.fix;

import lombok.extern.slf4j.Slf4j;
import quickfix.Application;
import quickfix.Message;
import quickfix.SessionID;

@Slf4j
public class FixInitorApplication implements Application {
    @Override
    public void onCreate(SessionID sessionId) {
        log.info("onCreate sessionId:{}", sessionId);
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.info("onLogon sessionId:{}", sessionId);
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info("onLogout sessionId:{}", sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        log.info("toAdmin sessionId:{}, message:{}", sessionId, message);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) {
        log.info("fromAdmin sessionId:{}, message:{}", sessionId, message);
    }

    @Override
    public void toApp(Message message, SessionID sessionId) {
        log.info("fromApp sessionId:{}, message:{}", sessionId, message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) {
        log.info("fromApp sessionId:{}, message:{}", sessionId, message);
    }
}
