package com.xiaomi.demo.java.net.nio;

import cn.hutool.http.ContentType;
import com.xiaomi.common.utils.JsonUtils;
import com.xiaomi.demo.design.User;
import com.xiaomi.demo.java.net.Response;
import com.xiaomi.web.core.enumeration.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
@Slf4j
public class NioServer {
    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    private final int serverPort;

    public NioServer(int serverPort) throws IOException {
        this.serverPort = serverPort;
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
    }

    public void start() throws IOException {
        serverSocketChannel.bind(new InetSocketAddress(serverPort));
        log.info("Server start!");
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //select会阻塞，知道有就绪连接写入selectionKeys
        while (!Thread.currentThread().isInterrupted()) {
            if (selector.select(100) == 0) {
                continue;
            }
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                //处理事件，不管是否可以处理完成，都删除 key。因为 soketChannel 为水平触发的，
                keys.remove();
                if (key.isAcceptable()) {
                    log.info("触发连接事件");
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    if (len == -1) {
                        socketChannel.close();
                    }
                    if (byteBuffer.remaining() > 0) {
                        System.out.print(getString(byteBuffer));
                    }
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    log.info("触发读事件");
                    ByteBuffer buffer = new Response()
                            .withStatus(HttpStatus.OK)
                            .withContentType(ContentType.JSON.toString())
                            .withBody(JsonUtils.toByteArray(new User(1L, "xiaomi")))
                            .getResponseByteBuffer();
                    socketChannel.write(buffer);
                }
            }
        }
    }

    public static String getString(ByteBuffer buffer) {
        try {
            return StandardCharsets.UTF_8.newDecoder().decode(buffer.asReadOnlyBuffer()).toString();
        } catch (Exception ex) {
            log.error("read buffer error", ex);
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        new NioServer(9999).start();
    }
}
