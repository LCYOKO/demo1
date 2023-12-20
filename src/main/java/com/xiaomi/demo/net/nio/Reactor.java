package com.xiaomi.demo.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class Reactor implements Runnable {
   private  ServerSocketChannel socketChannel;
   private  Selector selector;

    public Reactor(int port) throws IOException {
     socketChannel=ServerSocketChannel.open();
     socketChannel.configureBlocking(false);
     socketChannel.bind(new InetSocketAddress(port),1024);
     selector=Selector.open();
     SelectionKey key = socketChannel.register(selector, SelectionKey.OP_ACCEPT);
     key.attach(new Acceptor());
    }

    @Override
    public void run() {
        while(true){
            Set<SelectionKey> keys = selector.selectedKeys();

        }

    }

    private void dispatch(SelectionKey key){
        Runnable runnable = (Runnable) key.attachment();
        if(runnable!=null){
             runnable.run();
        }
    }

    private class Acceptor implements Runnable{

        @Override
        public void run() {

        }
    }


    private class Handler implements Runnable{
       private Selector selector;
       private SocketChannel socket;

        public Handler(Selector selector, SocketChannel socket) throws IOException {
            this.selector = selector;
            this.socket = socket;
            socket.configureBlocking(false);
//            socket.register(selector,)

        }

        @Override
        public void run() {

        }
    }

}
