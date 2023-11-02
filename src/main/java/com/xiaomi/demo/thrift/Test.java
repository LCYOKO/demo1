package com.xiaomi.demo.thrift;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.*;
import org.apache.thrift.transport.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author liuchiyun
 * @Date 2022/5/28 8:45 下午
 * @Version 1.0
 */
@Slf4j
public class Test {

    public static int SERVER_PORT = 7090;
    public static String SERVER_IP = "localhost";
    public static int TIMEOUT = 30;
    public static void main(String[] args) throws TTransportException {
  //startNoBlockServer();
  startClientAsync();
  //      startClientSync();
    }

    public static void startClientSync(){
        TTransport transport = null;
        try {
            transport = new TNonblockingSocket (SERVER_IP, SERVER_PORT, TIMEOUT);
            TProtocol protocol = new TBinaryProtocol(transport);
            BookService.Client client = new BookService.Client(protocol);
            //transport.open();
            List<Book> allBook = client.getAllBook();
            log.info("books:{}", allBook);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    public static void startClientAsync(){
            try {
                TAsyncClientManager clientManager = new TAsyncClientManager();
                // 客户端应该使用非阻塞 IO
                TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP,SERVER_PORT);
                // 协议与服务端需要一致
                TProtocolFactory tProtocolFactory = new TBinaryProtocol.Factory();

                final BookService.AsyncClient client = new BookService.AsyncClient(tProtocolFactory, clientManager, transport);
                //log.info("socket is open:{}",transport.startConnect());
                client.getAllBook(new AsyncMethodCallback(){

                    @Override
                    public void onComplete(Object response) {
                        System.out.println(response);
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
                //transport.open();
                Thread.sleep(10000);
            } catch (TTransportException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    //public static void startServer() throws TTransportException {
    //    System.out.println("UserServer start ....");
    //    TProcessor tprocessor = new BookService.Processor<BookService.Iface>(new MyBookServiceSync());
    //
    //    TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
    //    TServer.Args tArgs = new TServer.Args(serverTransport);
    //    tArgs.processor(tprocessor);
    //    tArgs.protocolFactory(new TBinaryProtocol.Factory());
    //    TServer server = new TSimpleServer(tArgs);
    //    server.serve();
    //}
    //
    public static void startNoBlockServer() throws TTransportException {

        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(
                SERVER_PORT);
        TBinaryProtocol.Factory profactory = new TBinaryProtocol.Factory();
        TProcessor tprocessor = new BookService.AsyncProcessor<BookService.AsyncIface>(
                new MyBookServiceAsync());
        TNonblockingServer.Args tArgs = new TNonblockingServer.Args(
                serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(profactory);
        TServer server = new TNonblockingServer(tArgs);
        server.serve();
    }

    //public static void startThreadPoolServer(){
    //
    //    TProcessor tprocessor = new UserService.Processor<UserService.Iface>(
    //            new UserServiceImpl());
    //
    //    TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
    //    TBinaryProtocol.Factory profactory = new TBinaryProtocol.Factory();
    //
    //    TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(
    //            serverTransport);
    //    tArgs.minWorkerThreads(10);
    //    tArgs.maxWorkerThreads(20);
    //    tArgs.processor(tprocessor);
    //    tArgs.protocolFactory(profactory);
    //    TServer server = new TThreadPoolServer(tArgs);
    //    server.serve();
    //}
    //
    //public static void startThreadSelectorServer(){
    //
    //    TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(
    //            SERVER_PORT);
    //    TBinaryProtocol.Factory profactory = new TBinaryProtocol.Factory();
    //
    //    TProcessor tprocessor = new UserService.Processor<UserService.Iface>(
    //            new UserServiceImpl());
    //
    //    TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(
    //            serverTransport);
    //    tArgs.processor(tprocessor);
    //    tArgs.protocolFactory(profactory);
    //
    //    TServer server = new TThreadedSelectorServer(tArgs);
    //    server.serve();
    //}
    //
    //public static void startHsHaServer(){
    //
    //    TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(
    //            SERVER_PORT);
    //    TBinaryProtocol.Factory profactory = new TBinaryProtocol.Factory();
    //    TProcessor tprocessor = new UserService.Processor<UserService.Iface>(
    //            new UserServiceImpl());
    //    THsHaServer.Args tArgs = new THsHaServer.Args(serverTransport);
    //    tArgs.processor(tprocessor);
    //    tArgs.protocolFactory(profactory);
    //    TServer server = new THsHaServer(tArgs);
    //    server.serve();
    //}

}
