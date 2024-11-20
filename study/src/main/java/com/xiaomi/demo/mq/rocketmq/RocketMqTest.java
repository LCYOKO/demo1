package com.xiaomi.demo.mq.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/24
 */
@Slf4j
public class RocketMqTest {
    private DefaultMQProducer publishTemplate;
    private TransactionMQProducer transactionMQProducer;
    private DefaultMQPushConsumer consumer;
    private final String TEST_TOPIC = "test";

    @Before
    public void init() throws MQClientException {
        publishTemplate = new DefaultMQProducer("testProducer");
        publishTemplate.setNamesrvAddr("127.0.0.1:9876");
        publishTemplate.start();
        transactionMQProducer = new TransactionMQProducer("trx");
        transactionMQProducer.setNamesrvAddr("127.0.0.1:9876");
        transactionMQProducer.setTransactionListener(new TransactionListenerImpl());
        transactionMQProducer.start();
        consumer = new DefaultMQPushConsumer("CID_JODIE_1");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setMaxReconsumeTimes(2);
    }

    @Test
    public void testSimpleSend() {
        Message msg = new Message(TEST_TOPIC, ("testSimpleSend ").getBytes());
        msg.setKeys("00001");
        try {
            SendResult sendResult = publishTemplate.send(msg);
            log.info("result:{}", sendResult);
        } catch (Exception e) {
            log.error("send message failed. message:{}", msg, e);
        }
    }

    @Test
    public void testBatchSend() {
        Message msg = new Message(TEST_TOPIC, ("testBatchSend").getBytes());
        try {
            SendResult sendResult = publishTemplate.send(Collections.singletonList(msg));
            log.info("result:{}", sendResult);
        } catch (Exception e) {
            log.info("send message failed. message:{}", msg, e);
        }
    }

    @Test
    public void testSentAsync() throws InterruptedException {
        Message msg = new Message("test", ("testSendAsync").getBytes());
        try {
            publishTemplate.send(Collections.singletonList(msg), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("result:{}", sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("send failed", throwable);
                }
            });
        } catch (Exception e) {
            log.info("send message failed. message:{}", msg, e);
        }
        Thread.sleep(1000);
    }

    @Test
    public void sendOneWay() {
        Message msg = new Message(TEST_TOPIC, ("sendOneWay").getBytes());
        try {
            publishTemplate.sendOneway(msg);
        } catch (Exception e) {
            log.info("send message failed. message:{}", msg, e);
        }
    }

    @Test
    public void testConsumer() throws MQClientException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        consumer.subscribe(TEST_TOPIC, "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //wrong time format 2017_0422_221800
//        consumer.setConsumeTimestamp("20181109221800");
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (Message message : msgs) {
                log.info("msg:{}, body:{}", message, new String(message.getBody()));
            }
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });
        consumer.start();
        latch.await();
    }

    @Test
    public void testSendMessageOrderly() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            int orderId = i % 2;
            Message msg = new Message("TopicTestjjj", tags[i % tags.length], "KEY" + i, ("Hello RocketMQ " + i).getBytes());
            SendResult sendResult = publishTemplate.send(msg, (mqs, msg1, arg) -> {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }, orderId);
            log.info("sned result:{}", sendResult);
        }
    }

    @Test
    public void testConsumerOrderly() throws MQClientException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_3");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(TEST_TOPIC, "TagA || TagC || TagD");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            final AtomicLong consumeTimes = new AtomicLong(0);

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                this.consumeTimes.incrementAndGet();
                if ((this.consumeTimes.get() % 2) == 0) {
                    return ConsumeOrderlyStatus.SUCCESS;
                } else if ((this.consumeTimes.get() % 5) == 0) {
                    context.setSuspendCurrentQueueTimeMillis(3000);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        latch.await();
    }

    @Test
    public void testSendDelayMessage() {
        Message msg = new Message("test", ("test delay message").getBytes());
        msg.setDelayTimeLevel(3);
        try {
            publishTemplate.sendOneway(msg);
        } catch (Exception e) {
            log.info("send message failed. message:{}", msg, e);
        }
    }

    @Test
    public void testTransactionMessage() throws MQClientException {
        Message msg = new Message("test", ("transaction msg").getBytes());
        SendResult result = transactionMQProducer.sendMessageInTransaction(msg, "hello");
        log.info("result:{}", result);
    }
}
