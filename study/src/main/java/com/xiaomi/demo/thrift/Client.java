package com.xiaomi.demo.thrift;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.*;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/18
 */
@Slf4j
public class Client {
    private BookService.Client client;
    private BookService.AsyncClient asyncClient;
    private String server = "127.0.0.1";
    private int port = 7777;

    public Client() {
        startClientSync();
    }

    public static void main(String[] args) throws TException {
        Client client = new Client();
        log.info("getBookInfo:{}", client.getBookInfo(1));
    }

    private void startClientSync() {
        try {
            TTransport transport = new TFramedTransport(new TSocket(server, port));
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            transport.open();
            client = new BookService.Client(protocol);
        } catch (Exception e) {
            log.error("出现问题", e);
        }
    }

    private void startClientAsync() {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(server, port);
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
            log.info("Client calls .....");
            final BookService.AsyncClient client = new BookService.AsyncClient(protocolFactory, clientManager, transport);
            client.getAllBook(new AsyncMethodCallback() {

                @Override
                public void onComplete(Object response) {
                    System.out.println("success");
                }

                @Override
                public void onError(Exception exception) {
                    System.out.println(exception);
                }
                //@SneakyThrows
                //@Override
                //public void onComplete(BookService.AsyncClient.getAllBook_call response) {
                //    System.out.println(response.getResult());
                //}
                //
                //@Override
                //public void onError(Exception exception) {
                //
                //}

                //
                //@Override
                //public void onComplete(Object response) {
                //    System.out.println(response);
                //}
                //
                //@Override
                //public void onError(Exception exception) {
                //
                //}
            });
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error("catch exception", e);
        }
    }

    public BookInfo getBookInfo(int bookId) throws TException {
        return client.getBookInfo(bookId);
    }
}
