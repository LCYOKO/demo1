package com.xiaomi.demo.rpc.thrift;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.*;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/18
 * <p>
 * Thrift 技术栈分层从下向上分别为：传输层，协议层，处理层和服务层
 * 传输层（Transport Layer）： 传输层负责直接茨贝格网络中读取和写入数据，它定义了具体的网络传输协议，比如说TCP/IP传输等
 * 协议层（Protocol Layer）：协议层定义了数据传输格式，负责网络传输数据的序列化和反序列化，比如说json，xml，二进制数据等
 * 处理层（Processor Layer）：处理层是具体的IDL生成的，封装了具体的底层网络传输和序列化方式，并委托给用户实现的handler处理
 * 服务层（Server Layer）：整合上述组件，提供具体的网络io模型，形成最终的服务
 * <p>
 * <p>
 * Thrift协议
 * Thrift可以让用户选择客户端与服务器之间传输通信协议类别，在传输协议上总体分为文本和二进制传输协议，为节约带宽，提高传输效率，一般采用二进制类型的传输协议为多数，常用的协议有以下几种
 * TBinaryProtocol：二进制编码格式进行数据传输
 * TCompactProtocol: 高效率，密集的二进制数据格式进行数据传输
 * TJSONProtocol：使用JSON文本数据编码协议进行数据传输
 * TSimpleJSONProtocol：只提供json只写协议，适用用于脚本语言解析
 * <p>
 * Thrift传输层
 * TIOStreamTransport：thrift中用于进行网络传输的transport，其内部基于对象构造时传入的InputStream/OutputStream进行IO操作
 * TSocket：thrift中用于进行网络IO的socket类，用于管理相关socket链接，该类继承自TIOStreamTransport
 * TNonblockingTransport:非阻塞网络IO类，用于异步网络通信
 * TNonblockingSocket:继承自TNonblockingTransport，用于管理异步socket，其内部的默认io实现是基于jdk提供的nio来支持的，当然，我们也可以重写一个基于netty的异步socket
 * TMemoryBuffer:对ByteArrayOutputStream进行了封装，用于字节输出流
 * TMemoryInputTransport:thrift自己实现的字节数组输入流，通过对其内部持有的字节数组进行实现
 * TFramedTransport：对字节IO流进行了一层封装，支持frame形式的字节IO。在thrift中，frame形式即表征数据长度（4字节）与结构化数据
 * <p>
 * Thrift提供的网络服务模型：单线程，多线程，时间驱动。从另外一个角度划分:阻塞服务模型，非阻塞服务模型
 * 阻塞服务模型： TSimpleServer,TThreadPoolServer
 * 非阻塞服务模型：TNonblockingServer,THshaServer,TThreadedSelectorServe
 */
@Data
@Slf4j
public class Server {
    public static int SERVER_PORT = 7777;
    private TServer server;

    public Server(ServerModel serverModel) throws TTransportException {
        switch (serverModel) {
            case BLOCK:
                startBlockServer();
                break;
            case BLOCK_THREAD_POOL:
                startThreadPoolServer();
                break;
            case NOT_BLOCK:
                startNoBlockServer();
                break;
            case NOT_BLOCK_THREAD:
                startHsHaServer();
                break;
            case NOT_BLOCK_THREAD_POOL:
            default:
                startThreadSelectorServer();
        }
    }

    public static void main(String[] args) throws TTransportException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Server server = new Server(ServerModel.NOT_BLOCK);
        countDownLatch.await();

    }

    /**
     * 采用最简单的阻塞IO，实现方法简洁明了，但是一次只能接受和处理一个socket连接，效率比较低，一般用于延时Thrift的工作过程
     */
    private void startBlockServer() throws TTransportException {
        TProcessor tprocessor = new BookService.Processor<BookService.Iface>(new MyBookServiceSync());
        TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
        TServer.Args tArgs = new TServer.Args(serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(new TBinaryProtocol.Factory());
        server = new TSimpleServer(tArgs);
        startServerAsync();
    }

    /**
     * 采用阻塞socket方式工作，主线程负责阻塞式监听是否有新的socket连接，具体业务处理交由一个线程池处理
     * TThreadedPoolServer 解决了TSimpleServer不支持的并发和多连接问题，引入线程池，实现的模型是one thread per connection
     * 优点：
     * 拆分监听线程（Accept Thread）与处理客户端连接的工作线程（Worker Thread）,数据读取和业务处理都交给线程池处理。因此并发量较大的时候新的连接能被及时的接受
     * 线程池模式比较适合服务器端能够阈值最多有多少个客户端并发的情况，这样每个请求都能被业务线程池及时处理，性能非常高
     * 缺点：
     * 线程池模式的处理能力受限于线程池的工作能力，如果线程处理业务逻辑较久，那么并发线程数大于线程池中的线程数，新的请求只能排队等待
     * 默认线程池允许创建的最大的线程数量为Integer.MAX_VALUE,可能会创建大量的线程，导致OOM
     */
    private void startThreadPoolServer() throws TTransportException {
        TBinaryProtocol.Factory profactory = getBinaryProtocolFactor();
        TProcessor tprocessor = getProcessor(true);
        TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
        TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(
                serverTransport);
        tArgs.minWorkerThreads(10);
        tArgs.maxWorkerThreads(20);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(profactory);
        server = new TThreadPoolServer(tArgs);
        startServerAsync();
    }

    /**
     * 单线程工作，但是采用NIO，利用IO多路复用模型处理socket就绪事件，对于有数据到来的socket进行数据读取工作，
     * 对于有数据发送的socket则进行数据发送操作，对于监听socket则产生一个新的socket注册到selector,使用单Reactor多线程模式。
     * 优点：
     * 相对于TSimpleServer效率提升主要体现在IO多路复用上，TNonblockingServer采用非阻塞IO,对accept/read/write等io事件进行监控和处理，同时监控多个socket变化
     * 缺点：
     * 在业务处理上还是单线程顺序来完成，当业务处理比较复杂，耗时的时候，例如某些接口需要读取数据库执行时间比较长，会导致整个服务阻塞住，此时该模式效率不高，因为多个调用请求任务依然是顺序一个一个执行
     */
    private void startNoBlockServer() throws TTransportException {
        System.out.println("UserServer start ....");
        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(SERVER_PORT);
        TBinaryProtocol.Factory profactory = new TBinaryProtocol.Factory();
        TProcessor tprocessor = new BookService.Processor<BookService.Iface>(
                new MyBookServiceSync());
        TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(profactory);
        server = new TNonblockingServer(tArgs);
        server.serve();
    }

    /**
     * 鉴于TNonblockingServer缺点，THsHaServer继承TNonblockingServer，引入线程池提高任务处理的并发能力
     * 优点：
     * 与TNonblockingServer模式相比，THsHaServer在完成数据读取后，将业务处理过程交给线程池去处理，主线程直接返回下一次循环操作，效率大大提升
     * 缺点
     * 主线程仍然完成所有的socket的连接监听，数据读取，数据写入操作，当并发请求数量大的时候，且发送数据量过多的时候，监听socket新的连接请求不能及时的接受
     */
    private void startHsHaServer() throws TTransportException {
        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(
                SERVER_PORT);
        TBinaryProtocol.Factory factory = getBinaryProtocolFactor();
        TProcessor tprocessor = getProcessor(true);
        THsHaServer.Args tArgs = new THsHaServer.Args(serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(factory);
        tArgs.executorService(Executors.newSingleThreadExecutor());
        server = new THsHaServer(tArgs);
        startServerAsync();

    }

    /**
     * TThreadedSelectorServer是对THsHaServer的一种扩充，它将selector中的读写IO事件（read/write）从主线程分离处来，同时引入worker工作线程池.
     * 内部有几下几个部分组成：
     * 1.一个AcceptThread专门用来处理socket的连接事件
     * 2.若干个SelectorThread专门用来处理业务socket的网络IO操作，所有的网络的读写都是由这些线程完成
     * 3.一个负载均衡器 SelectorThreadLoadBalancer对象，主要用于AcceptThread线程接受到一个新的socket连接请求的时候，将这个新的连接请求分配到那个SelectorThread线程
     * 4.一个ExecutorService类型的工作线程池，在SelectorThread的线程中，监听到有业务的socket中有调用请求过来，则将请求的数据如读取之后，交给ExecutorSErvice线程池完成此次调用的具体操作，主要用于处理每个rpc请求的handler回调处理
     */
    private void startThreadSelectorServer() throws TTransportException {
        TBinaryProtocol.Factory factory = getBinaryProtocolFactor();
        TProcessor tprocessor = getProcessor(true);

        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(
                SERVER_PORT);
        TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(
                serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(factory);
        tArgs.executorService(Executors.newSingleThreadExecutor());
        server = new TThreadedSelectorServer(tArgs);
        startServerAsync();
    }

    private void startServerAsync() {
        try {
            new Thread(() -> server.serve()).start();
        } catch (Exception ex) {
            log.error("start server error");
        }
    }

    private TBinaryProtocol.Factory getBinaryProtocolFactor() {
        return new TBinaryProtocol.Factory();
    }

    private TProcessor getProcessor(boolean useSync) {
        if (useSync) {
            return new BookService.Processor<BookService.Iface>(
                    new MyBookServiceSync());
        } else {
            return new BookService.AsyncProcessor<BookService.AsyncIface>(
                    new MyBookServiceAsync());
        }
    }

    enum ServerModel {
        BLOCK,
        BLOCK_THREAD_POOL,
        NOT_BLOCK,
        NOT_BLOCK_THREAD,
        NOT_BLOCK_THREAD_POOL
    }
}
