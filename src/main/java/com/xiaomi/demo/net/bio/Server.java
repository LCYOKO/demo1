package com.xiaomi.demo.net.bio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8999);
        while(true){
            Socket socket = serverSocket.accept();
            System.out.println(socket);
            new Thread(()->{
                PrintWriter writer=null;
                try {
                    writer = new PrintWriter(socket.getOutputStream());
                    writer.println("hello world");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    writer.close();
                }
            }).start();
        }
    }
}
