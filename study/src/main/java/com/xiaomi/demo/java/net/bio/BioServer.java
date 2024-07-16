package com.xiaomi.demo.java.net.bio;

import com.xiaomi.common.utils.JsonUtils;
import com.xiaomi.demo.design.User;
import com.xiaomi.demo.java.net.Response;
import com.xiaomi.web.core.enumeration.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/5
 */
@Slf4j
public class BioServer {
    private final ServerSocket serverSocket;
    private final ThreadPoolExecutor workerPool;

    public BioServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        workerPool = new ThreadPoolExecutor(10, 10, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void start() throws IOException {
        log.info("start server at port:{} success", serverSocket.getLocalPort());
        while (true) {
            Socket socket = serverSocket.accept();
            workerPool.execute(() -> process(socket));
        }
    }

    private void process(Socket socket) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream())) {
            log.info("receive msg:{}", getRequest(bufferedInputStream));
            byte[] responseBytes = new Response()
                    .withStatus(HttpStatus.OK)
                    .withBody(JsonUtils.toByteArray(new User(1L, "lisi")))
                    .withContentType("application/json")
                    .getResponseBytes();
            log.info("response msg:{}", new String(responseBytes));
            socket.getOutputStream().write(responseBytes);
            socket.getOutputStream().flush();
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    private String getRequest(BufferedInputStream bufferedInputStream) throws IOException {
        byte[] bytes = new byte[1024 * 4];
        int read = bufferedInputStream.read(bytes);
        return new String(bytes, 0, read);
    }

    public static void main(String[] args) throws IOException {
        new BioServer(9999).start();
    }

}
