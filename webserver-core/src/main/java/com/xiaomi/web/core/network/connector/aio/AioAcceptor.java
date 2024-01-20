package com.xiaomi.web.core.network.connector.aio;

import com.xiaomi.web.core.network.endpoint.aio.AioEndpoint;
import com.xiaomi.web.core.network.wrapper.aio.AioSocketWrapper;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Slf4j
public class AioAcceptor implements CompletionHandler<AsynchronousSocketChannel, Void> {
    private AioEndpoint aioEndpoint;

    public AioAcceptor(AioEndpoint aioEndpoint) {
        this.aioEndpoint = aioEndpoint;
    }

    @Override
    public void completed(AsynchronousSocketChannel client, Void attachment) {
        aioEndpoint.accept();
        aioEndpoint.execute(new AioSocketWrapper(aioEndpoint, client));
    }

    @Override
    public void failed(Throwable e, Void attachment) {
        log.error("accept failed... attachment:{}", attachment, e);
    }
}
