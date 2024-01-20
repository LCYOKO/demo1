package com.xiaomi.demo.net.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/18
 */
public class Handler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Handler.class.getName());
    /**
     * 新选择器
     */
    private Selector selector;
    /**
     * 客户端Channel
     */
    private final SocketChannel channel;

    /**
     * SelectionKey集合
     */
    private SelectionKey key;
    /**
     * 输入缓冲区
     */
    private final ByteBuffer input = ByteBuffer.allocate(1024);

    /**
     * 输出缓冲区
     */
    private final ByteBuffer output = ByteBuffer.allocate(1024);

    public Handler(SocketChannel channel) throws IOException {
        this.channel = channel;
        // 设置客户端连接为非阻塞模式
        channel.configureBlocking(false);
        // 为客户端创建一个新的多路复用器
        selector = Selector.open();
        // 注册客户端Channel的读事件
        key = channel.register(selector, SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        try {
            while (selector.isOpen() && channel.isOpen()) {
                // 等待客户端事件发生
                Set<SelectionKey> keys = select();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey innerKey = iterator.next();
                    iterator.remove();
                    // 如果当前是读事件，则读取数据
                    if (innerKey.isReadable()) {
                        read(innerKey);
                    } else if (innerKey.isWritable()) {
                        // 如果当前是写事件，则写入数据
                        write(innerKey);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里处理的主要目的是处理Jdk的一个bug，该bug会导致Selector被意外触发，但是实际上没有任何事件到达，
     * 此时的处理方式是新建一个Selector，然后重新将当前Channel注册到该Selector上
     *
     * @return SelectionKey集合
     * @throws IOException IO异常
     */
    private Set<SelectionKey> select() throws IOException {
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        if (keys.isEmpty()) {
            int interestOps = key.interestOps();
            selector = Selector.open();
            key = channel.register(selector, interestOps);
            return select();
        }
        return keys;
    }

    /**
     * 读取客户端发送的数据
     *
     * @param key SelectionKey
     * @throws IOException IO异常
     */
    private void read(SelectionKey key) throws IOException {
        int count = channel.read(input);
        if (count <= 0) {
            return;
        }
        input.flip();
        // 对读取的数据进行业务处理
        process();
        input.clear();
        // 读取完成后监听写入事件
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) throws IOException {
        output.flip();
        if (channel.isOpen()) {
            // 当有写入事件时，将业务处理的结果写入到客户端Channel中
            channel.write(output);
            channel.close();
            output.clear();
        }
    }

    /**
     * 进行业务处理，并且获取处理结果。本质上，基于Reactor模型，如果这里成为处理瓶颈，
     * 则直接将其处理过程放入线程池即可，并且使用一个Future获取处理结果，最后写入客户端Channel
     */
    private void process() {
        Charset charset = StandardCharsets.UTF_8;
        String message = new String(charset.decode(input).array());
        logger.info("【Handler】" + Thread.currentThread().getName() + "-" + message);
        //response
        output.put(charset.encode("hello client"));
    }
}
