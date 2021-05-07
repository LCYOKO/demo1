package com.xiaomi.demo.net.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author：liuchiyun
 * @Date: 2021/5/7
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 8999));
        BufferedReader reader = null;
        try {
           reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
           reader.lines().forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reader.close();
        }
    }
}
