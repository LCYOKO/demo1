package com.xiaomi.demo.mq.rabbit.api;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/24
 */
@Slf4j
public class RabbitMqTest {
    private Channel channel;
    private Connection connection;
    private static final String DIRECT_EXCHANGE_NAME = "direct_exchange";
    private static final String FANOUT_EXCHANGE_NAME = "fanout_exchange";
    private static final String TOPIC_EXCHANGE_NAME = "topic_exchange";
    private static final String DLX_EXCHANGE_NAME = "dlx_exchange";
    public static final String EXCHANGE_NAME = "main_exchange";
    public static final String BAK_EXCHANGE_NAME = "backup_exchange";

    @Before
    public void init() throws IOException, TimeoutException {
        // 1.创建一个ConnectionFactory，并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
    }

    @Test
    public void testSendDirect() throws IOException {
        String routingKey = "error";
        String msg = "Hello RabbitMQ Consumer Message";
        for (int i = 0; i < 5; i++) {
            // mandatory 消息路由失败是否返回发送端
            // immediate 如果消息路由到的队列是否返回发送端，投递失败
            channel.basicPublish(DIRECT_EXCHANGE_NAME, routingKey, null, msg.getBytes());
        }
    }

    @Test
    public void testDirectConsumer() throws IOException, InterruptedException {
        // 4.创建交换器
        // 因为不知道生产者和消费者程序哪个先启动，所以一般的做法是在生产者和消费者2边都创建交换器（有的话不会重复创建）
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
        // 5.声明（创建）一个队列
        //  queue: 队列名字
        //  durable: 是否持久化
        //  exclusive: 是否独占
        //  autoDelete: 队列脱离exchange，自动删除
        //  arguments: 扩展参数
        final String queue1 = "test_queue1";
        final String bindingKey = "error";
        channel.queueDeclare(queue1, true, false, false, null);
        // 6.绑定交换机和队列
        channel.queueBind(queue1, DIRECT_EXCHANGE_NAME, bindingKey);
        channel.basicConsume(queue1, true, new MyConsumer(channel));
        Thread.sleep(20000);
    }

    @Test
    public void testExclusiveConsumer() throws IOException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
//        CyclicBarrier barrier = new CyclicBarrier(3);

        Channel channel1 = connection.createChannel();
        Channel channel2 = connection.createChannel();
        final String queue = "test_queue_exclusive";
        channel1.queueDeclare(queue, true, true, false, Collections.emptyMap());
        channel2.queueDeclare(queue, true, true, false, Collections.emptyMap());
        channel1.queueBind(queue, DIRECT_EXCHANGE_NAME, "error");
        channel1.basicConsume(queue, true, new MyConsumer(channel1, "channel1"));
        channel2.basicConsume(queue, true, new MyConsumer(channel2, "channel2"));
        countDownLatch.await();
    }

    @Test
    public void testFanoutPublish() throws IOException {
        String[] logLevel = {"info", "warning", "error"};
        for (int i = 0; i < 3; i++) {
            String routingKey = logLevel[i % 3];
            String message = "hello rabbitmq " + i;
            channel.basicPublish(FANOUT_EXCHANGE_NAME, routingKey, null, message.getBytes());
            log.info("send message, routingKey: {}, message: {}", routingKey, message);
        }
    }

    @Test
    public void testFanoutConsumer() throws IOException, InterruptedException {
        // 声明一个交换机
        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueNameA = "allQueueA";
        String queueNameB = "allQueueB";
        String bindingKey = "info";

        channel.queueDeclare(queueNameA, false, false, false, null);
        channel.queueDeclare(queueNameB, false, false, false, null);
        channel.queueBind(queueNameA, FANOUT_EXCHANGE_NAME, bindingKey);
        channel.queueBind(queueNameB, FANOUT_EXCHANGE_NAME, bindingKey);

        channel.basicConsume(queueNameA, true, new MyConsumer(channel));
        channel.basicConsume(queueNameB, true, new MyConsumer(channel));
        Thread.sleep(10000);
    }

    @Test
    public void testTopicSend() throws IOException {
        String[] logLevel = {"info", "warning", "error"};
        String[] module = {"driver", "login", "crm"};
        String[] score = {"A", "B", "C"};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    String routingKey = String.join(".", Arrays.asList(logLevel[i % 3], module[j % 3], score[k % 3]));
                    String message = "hello rabbitmq routingKey is " + routingKey;
                    channel.basicPublish(TOPIC_EXCHANGE_NAME, routingKey, null, message.getBytes());
                    log.info("send message, routingKey: {}, message: {}", routingKey, message);
                }
            }
        }
    }

    @Test
    public void testTopicConsumer() throws IOException, InterruptedException {
        // 声明一个交换机
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueNameA = "allQueue";
        String bindingKeyA = "#";
        String queueNameB = "crmQueue";
        String bindingKeyB = "#.crm.#";
        channel.queueDeclare(queueNameA, false, false, false, null);
        channel.queueBind(queueNameA, TOPIC_EXCHANGE_NAME, bindingKeyA);
        channel.queueDeclare(queueNameB, false, false, false, null);
        channel.queueBind(queueNameB, TOPIC_EXCHANGE_NAME, bindingKeyB);
        channel.basicConsume(queueNameA, true, new MyConsumer(channel));
        channel.basicConsume(queueNameB, true, new MyConsumer(channel));
        Thread.sleep(10000);
    }

    @Test
    public void testGetMsg() throws IOException, InterruptedException {
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
        final String queue1 = "test_queue2";
        final String bindingKey = "error";
        channel.queueDeclare(queue1, true, false, false, null);
        channel.queueBind(queue1, DIRECT_EXCHANGE_NAME, bindingKey);
        while (true) {
            GetResponse getResponse = channel.basicGet(queue1, true);
            if (null != getResponse) {
                log.info("get message, routingKey: {}, message: {}", getResponse.getEnvelope().getRoutingKey(), new String(getResponse.getBody()));
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void testSendFailed() throws IOException, InterruptedException {
        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {
            log.error("---------handle  return----------");
            log.error("replyCode: " + replyCode);
            log.error("replyText: " + replyText);
            log.error("exchange: " + exchange);
            log.error("routingKey: " + routingKey);
            log.error("properties: " + properties);
            log.error("body: " + new String(body));
        });
        channel.basicPublish(DIRECT_EXCHANGE_NAME, "error1231", true, null, "hello rabbitmq".getBytes());
        Thread.sleep(10000);
    }

    @Test
    public void testConfirm() throws IOException, InterruptedException {
        // 启用发布者确认模式
        channel.confirmSelect();
        String routingKey = "error11123";
        for (int i = 0; i < 10; i++) {
            String message = "hello rabbitmq " + i;
            channel.basicPublish(DIRECT_EXCHANGE_NAME, routingKey, null, message.getBytes());
            // 一条一条确认，返回为true，则表示发送成功
            if (channel.waitForConfirms()) {
                log.info("send success, routingKey: {}, message: {}", routingKey, message);
            } else {
                log.info("send fail, routingKey: {}, message: {}", routingKey, message);
            }
        }
    }

    @Test
    public void testConfirmAsync() throws IOException, InterruptedException {
        channel.confirmSelect();
        //6 添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleNack(long deliveryTag, boolean multiple) {
                log.info("nAck deliverTag:{}", deliveryTag);
            }

            @Override
            public void handleAck(long deliveryTag, boolean multiple) {
                log.info("ack deliverTag:{}", deliveryTag);
            }
        });
        String routingKey = "error11123";
        for (int i = 0; i < 10; i++) {
            String message = "hello rabbitmq " + i;
            channel.basicPublish(DIRECT_EXCHANGE_NAME, routingKey, null, message.getBytes());
        }
        Thread.sleep(10000);
    }

    @Test
    public void testBackupExchange() throws IOException {
        String[] logLevel = {"error", "info", "warning"};
        for (int i = 0; i < 3; i++) {
            String routingKey = logLevel[i % 3];
            String message = "hello rabbitmq " + i;
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            log.info("send message, routingKey: {}, message: {}", routingKey, message);
        }
    }

    @Test
    public void testBackupConsumer() throws IOException, InterruptedException {
        // 备用交换器,未能正确路由的消息都会被丢到备用交换机中
        channel.exchangeDeclare(BAK_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        Map<String, Object> argsMap = new HashMap<>(2);
        argsMap.put("alternate-exchange", BAK_EXCHANGE_NAME);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, false, argsMap);

        String queueName1 = "notErrorQueue";
        channel.queueDeclare(queueName1, false, false, false, null);
        // fanout类型的交换器bindingKey不起作用，这里随便写了一个#
        channel.queueBind(queueName1, BAK_EXCHANGE_NAME, "#");

        String queueName2 = "errorQueue";
        String bindingKey = "error";
        channel.queueDeclare(queueName2, false, false, false, null);
        channel.queueBind(queueName2, EXCHANGE_NAME, bindingKey);

        channel.basicConsume(queueName1, true, new MyConsumer(channel));
        channel.basicConsume(queueName2, true, new MyConsumer(channel));
        Thread.sleep(10000);
    }

    /**
     * 关键的问题是消息在正确存入RabbitMQ之后，还需要有一段时间（这个时间很短，但不可忽视）才能存入磁盘之中，
     * RabbitMQ并不是为每条消息都做fsync的处理，可能仅仅保存到cache中而不是物理磁盘上，
     * 在这段时间内RabbitMQ broker发生crash, 消息保存到cache但是还没来得及落盘，那么这些消息将会丢失。
     * 那么这个怎么解决呢？首先可以引入RabbitMQ的mirrored-queue即镜像队列，这个相当于配置了副本，
     * 当master在此特殊时间内crash掉，可以自动切换到slave，这样有效的保障了HA, 除非整个集群都挂掉，
     * 这样也不能完全的100%保障RabbitMQ不丢消息，但比没有mirrored-queue的要好很多，
     * 很多现实生产环境下都是配置了mirrored-queue的。
     * RabbitMQ的可靠性涉及producer端的确认机制、broker端的镜像队列的配置以及consumer端的确认机制，
     * 要想确保消息的可靠性越高，那么性能也会随之而降，鱼和熊掌不可兼得，关键在于选择和取舍。
     * <p>
     * 消息什么时候刷到磁盘？
     * 写入文件前会有一个Buffer,大小为1M,数据在写入文件时，首先会写入到这个Buffer，如果Buffer已满，则会将Buffer写入到文件（未必刷到磁盘）。
     * 有个固定的刷盘时间：25ms,也就是不管Buffer满不满，每个25ms，Buffer里的数据及未刷新到磁盘的文件内容必定会刷到磁盘。
     * 每次消息写入后，如果没有后续写入请求，则会直接将已写入的消息刷到磁盘：使用Erlang的receive x after 0实现，
     * 只要进程的信箱里没有消息，则产生一个timeout消息，而timeout会触发刷盘操作。
     */
    @Test
    public void testMsgPersistence() throws IOException {
        String routingKey = "error";
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        // 主要就是将deliveryMode设置为2
        builder.deliveryMode(2);
        // 10000ms过期
        builder.expiration("10000");
        AMQP.BasicProperties properties = builder.build();
        for (int i = 0; i < 10; i++) {
            String message = "hello rabbit " + i;
            channel.basicPublish(DIRECT_EXCHANGE_NAME, routingKey, properties, message.getBytes());
            log.info("send message, routingKey: {}, message: {}", routingKey, message);
        }
    }

    @Test
    public void testDlx() throws IOException, TimeoutException, InterruptedException {
        final String queue1 = "test_queue11";
        final String bindingKey = "error";
        Map<String, Object> argsMap = new HashMap<>(4);
        argsMap.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        //死信队列重新设置rtKey
//        argsMap.put("x-dead-letter-routing-key", "dlx_key");
        //设置过期时间
//        args.put("x-message-ttl " , 6000);
        channel.exchangeDeclare(DLX_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        channel.queueDeclare(queue1, true, false, false, argsMap);
        channel.queueBind(queue1, DIRECT_EXCHANGE_NAME, bindingKey);

        final String dlxQueue = "dlx_queue";
        final String dlxRouteKey = "#";
        channel.queueDeclare(dlxQueue, true, false, false, null);
        channel.queueBind(dlxQueue, DLX_EXCHANGE_NAME, dlxRouteKey);

        channel.basicConsume(queue1, false, new MyConsumer(channel));
        channel.basicConsume(dlxQueue, true, new MyConsumer(channel));
        Thread.sleep(20000);
    }

    @Test
    public void testQpsLimit() throws IOException, InterruptedException {
        final String queue1 = "test_queue1";
        final String bindingKey = "error";
        channel.queueDeclare(queue1, true, false, false, null);
        // 6.绑定交换机和队列
        channel.queueBind(queue1, DIRECT_EXCHANGE_NAME, bindingKey);
        channel.basicConsume(queue1, true, new MyConsumer(channel));
        // prefetchSize为批量取的消息的总大小，0为不限制
        // prefetchCount为消费完3条（3条消息被ack）才再次推送
        // global为true表示对channel进行限制，否则对每个消费者进行限制,一个信道允许有多个消费者
        channel.basicQos(0, 3, false);
        Thread.sleep(20000);
    }
}
