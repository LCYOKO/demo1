package com.xiaomi.demo.java.net.selector;

import com.xiaomi.common.utils.JsonUtils;
import com.xiaomi.demo.design.User;
import com.xiaomi.demo.java.net.Response;
import com.xiaomi.web.core.enumeration.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/18
 */
@Slf4j
public class SelectorServer {

    private final List<SocketChannel> channelList = new ArrayList<>();

    private final ServerSocketChannel serverSocketChannel;

    private final int serverPort;

    private final Selector mainSelector;

    private final Selector subSelector;

    private final ExecutorService SUB_SELECTOR_THREAD_POOL = Executors.newFixedThreadPool(1);

    private SelectorServer(int port) {
        this.serverPort = port;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(serverPort));
            serverSocketChannel.configureBlocking(false);
            mainSelector = Selector.open();
            subSelector = Selector.open();
            SelectionKey key = serverSocketChannel.register(mainSelector, SelectionKey.OP_ACCEPT);
            key.attach(serverSocketChannel);
        } catch (Exception ex) {
            log.error("new server failed", ex);
            throw new RuntimeException("new server failed", ex);
        }
    }

    public void start() throws IOException {
        SUB_SELECTOR_THREAD_POOL.execute(this::runSubSelector);
        while (true) {
            mainSelector.select();
            Iterator<SelectionKey> iterator = mainSelector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.attachment();
                    SocketChannel socketChannel = serverChannel.accept();
                    channelList.add(socketChannel);
                    socketChannel.configureBlocking(false);
                    socketChannel.register(subSelector, SelectionKey.OP_READ).attach(socketChannel);
                }
            }
        }
    }

    private void runSubSelector() {
        while (true) {
            try {
                log.info("start select");
                int select = subSelector.select(1000);
                if (select <= 0) {
                    continue;
                }
            } catch (Exception ex) {
                log.error("subSelector select failed", ex);
                throw new RuntimeException("subSelector select failed", ex);
            }
            Iterator<SelectionKey> keyIterator = subSelector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey curKey = keyIterator.next();
                SocketChannel socketChannel = (SocketChannel) curKey.attachment();
                keyIterator.remove();
                if (curKey.isReadable()) {
                    Charset charset = StandardCharsets.UTF_8;
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    try {
                        int read = socketChannel.read(buffer);
                        if (read > 0) {
                            buffer.flip();
                            String request = charset.decode(buffer).toString();
                            log.info(request);
                        }
                        ByteBuffer responseByteBuffer = new Response()
                                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                                .withContentType(ContentType.APPLICATION_JSON.toString())
                                .withBody(JsonUtils.toByteArray(new User(2L, "xiaomi")))
                                .getResponseByteBuffer();
                        socketChannel.write(responseByteBuffer);
                    } catch (Exception e) {
                        log.info("error", e);
                    }
                }
            }
        }
    }

    private void startHeatBeatAsync() {
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                channelList.forEach(channel -> {
                    Charset charset = StandardCharsets.UTF_8;
                    try {
                        channel.write(charset.encode("Long ServerResponse"));
                        log.info("Send Long Response");
                    } catch (Exception e) {
                        log.error("heart beat error. channel:{}", channel, e);
                    }
                });
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        new SelectorServer(9999).start();
    }
}
