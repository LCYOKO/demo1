package com.xiaomi.web.core.network.connector.bio;

import com.xiaomi.web.core.network.dispatcher.bio.BioDispatcher;
import com.xiaomi.web.core.network.endpoint.bio.BioEndpoint;
import com.xiaomi.web.core.network.wrapper.bio.BioSocketWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

/**
 * @author sinjinsong
 * @date 2018/5/4
 */
@Slf4j
public class BioAcceptor implements Runnable {
    private BioEndpoint server;
    private BioDispatcher dispatcher;
    
    public BioAcceptor(BioEndpoint server,BioDispatcher dispatcher) {
        this.server = server;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        while (server.isRunning()) {
            Socket client;
            try {
                //TCP的短连接，请求处理完即关闭
                client = server.accept();
                dispatcher.doDispatch(new BioSocketWrapper(client));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
