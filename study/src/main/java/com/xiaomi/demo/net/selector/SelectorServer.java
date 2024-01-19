package com.xiaomi.demo.net.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class SelectorServer {
    private static final Logger logger = LoggerFactory.getLogger(Selector.class.getName());

    private static List<SocketChannel> channelList = new ArrayList<>();

    private final ServerSocketChannel serverSocketChannel;

    private final Selector mainSelector;

    private final Selector subSelector;

    private final ExecutorService MAIN_SELECTOR_THREAD_POOL = Executors.newFixedThreadPool(1);

    private SelectorServer(int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8888));
            serverSocketChannel.configureBlocking(false);
            logger.info("SelectorServer Binded");
            mainSelector = Selector.open();
            subSelector = Selector.open();
            SelectionKey key = serverSocketChannel.register(mainSelector, SelectionKey.OP_ACCEPT);
            key.attach(serverSocketChannel);
        } catch (Exception ex) {
            logger.error("new server failed", ex);
            throw new RuntimeException("new server failed", ex);
        }
        startHeatBeatAsync();
    }

    private void run() throws IOException {
        while (true) {
            mainSelector.select();
            Iterator<SelectionKey> iterator = mainSelector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.attachment();
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);
                    MAIN_SELECTOR_THREAD_POOL.execute(() -> {
                        try {
                            socketChannel.register(subSelector, SelectionKey.OP_READ);
                        } catch (Exception ex) {
                            logger.error("subSelector register failed", ex);
                            throw new RuntimeException("subSelector register failed", ex);
                        }
                        try {
                            subSelector.select();
                        } catch (Exception ex) {
                            logger.error("subSelector select failed", ex);
                            throw new RuntimeException("subSelector select failed", ex);
                        }
                        Iterator<SelectionKey> keyIterator = subSelector.selectedKeys().iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey curKey = keyIterator.next();
                            if (curKey.isReadable()) {
                                Charset charset = StandardCharsets.UTF_8;
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                try {
                                    int read = socketChannel.read(buffer);
                                    if (read > 0) {
                                        buffer.flip();
                                        String request = charset.decode(buffer).toString();
                                        logger.info(request);
                                    }
                                    socketChannel.write(charset.encode("SelectorServer Response"));
                                    channelList.add(socketChannel);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            keyIterator.remove();
                        }
                    });
                }
                iterator.remove();
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
                        logger.info("Send Long Response");
                    } catch (IOException e) {
                        logger.error("heart beat error. channel:{}", channel, e);
                    }
                });
            }
        }).start();
    }
}
