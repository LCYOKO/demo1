package com.xiaomi.demo.net.bio;

import com.xiaomi.demo.net.SocketWrapper;
import lombok.Getter;

import java.io.IOException;
import java.net.Socket;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
public class BioSocketWrapper implements SocketWrapper {
    @Getter
    private final Socket socket;

    public BioSocketWrapper(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
