package com.xiaomi.demo.fix;

import lombok.extern.slf4j.Slf4j;
import quickfix.*;
import quickfix.field.MsgType;

@Slf4j
public class FixAcceptorApplication extends MessageCracker implements Application {
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

    @Override
    protected void onMessage(Message message, SessionID sessionId) {
        try {
            String msgType = message.getHeader().getString(35);
            Session session = Session.lookupSession(sessionId);
            switch (msgType) {
                // 登陆
                case MsgType.LOGON:
                    session.logon();
                    session.sentLogon();
                    break;
                // 心跳
                case MsgType.HEARTBEAT:
                default:
                    session.generateHeartbeat();
            }
        } catch (Exception e) {
            log.error("process message:{} failed.", message, e);
        }
    }
}
