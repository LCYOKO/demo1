package com.xiaomi.demo.fix;

import lombok.extern.slf4j.Slf4j;
import quickfix.*;

@Slf4j
public class FixInitorApplication implements Application {
    @Override
    public void onCreate(SessionID sessionID) {
        log.info("onCreate sessionId:{}", sessionID);
    }

    @Override
    public void onLogon(SessionID sessionID) {
        log.info("onLogon sessionId:{}",sessionID);
    }

    @Override
    public void onLogout(SessionID sessionID) {
        log.info("onLogout sessionId:{}", sessionID);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        log.info("toAdmin sessionId:{}, message:{}", sessionID, message);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("fromAdmin sessionId:{}, message:{}", sessionID, message);
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        log.info("fromApp sessionId:{}, message:{}", sessionID, message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.info("fromApp sessionId:{}, message:{}", sessionID, message);
    }
}
