package com.xiaomi.demo.java.net.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class Reactor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocket;

    /**
     * subReactor线程池
     */
    private final ExecutorService subReactorThreadGroup = Executors.newFixedThreadPool(3);
    /**
     * handler线程池
     */
    private final ExecutorService handlerThreadGroup = Executors.newFixedThreadPool(3);

    public Reactor(int port) throws IOException {
        // 创建服务端的ServerSocketChannel
        serverSocket = ServerSocketChannel.open();
        // 设置为非阻塞模式
        serverSocket.configureBlocking(false);
        // 创建一个Selector多路复用器
        selector = Selector.open();
        //注册ACCEPT事件
        SelectionKey key = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        // 绑定服务端端口
        serverSocket.bind(new InetSocketAddress(port));
        key.attach(serverSocket);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 服务端使用一个线程不断等待客户端的连接到达
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    //客户端连接获取(Key一般是OP_ACCEPT)，并且进行分发
                    dispatch(iterator.next());
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听到客户端连接事件后将其分发给Acceptor
     * @param key SelectionKey
     */
    private void dispatch(SelectionKey key){
        ServerSocketChannel socketChannel = (ServerSocketChannel) key.attachment();
        //为服务端Channel绑定一个Acceptor
        subReactorThreadGroup.execute(new Acceptor(socketChannel, handlerThreadGroup));
    }

    public static void main(String[] args) throws IOException {
        new Reactor(8888).run();
    }

}
