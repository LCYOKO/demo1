package com.xiaomi.demo.fix;

import lombok.extern.slf4j.Slf4j;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix42.NewOrderSingle;

import java.time.LocalDateTime;

/**
 * @author lcy
 */
@Slf4j
public class FixInitor {
    private SessionSettings settings;
    private Initiator initiator;
    private SessionID sessionID;
    public FixInitor() throws ConfigError {
        try {
            settings = new SessionSettings("src/main/resources/quickfix-initor.properties");
        } catch (ConfigError configError) {
            log.error("Warning config error",configError);
        }
        FixInitorApplication application = new FixInitorApplication();
        MessageFactory messageFactory = new DefaultMessageFactory();
        SLF4JLogFactory logFactory = new SLF4JLogFactory(settings);
        FileStoreFactory fileStoreFactory = new FileStoreFactory(settings);
        initiator = new ThreadedSocketInitiator(application, fileStoreFactory, settings, logFactory,
                messageFactory);
        this.sessionID = new SessionID("FIX.4.2","CLIENT","SERVER");
    }

    public void start() throws ConfigError {
        initiator.start();
    }

    public void sendOrder() throws SessionNotFound {
        NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID("123"), new HandlInst('1'), new Symbol("YRD"),
                new Side(Side.BUY), new TransactTime(LocalDateTime.now()), new OrdType(OrdType.MARKET));
        Session.sendToTarget(newOrder, sessionID);
    }

    public void stop(){

    }
}
