//package com.xiaomi.demo.mq.rocketmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.LocalTransactionState;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.client.producer.TransactionListener;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;
//import org.junit.Test;
//
//import java.util.Collections;
//
///**
// * @Author: liuchiyun
// * @Date: 2023/11/29
// */
//@Slf4j
//public class Example {
//    @Test
//    public void testProducer() throws MQClientException {
//        DefaultMQProducer producer = new DefaultMQProducer("testProducer");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message msg = new Message("test", ("Hello RocketMQ ").getBytes());
//        try {
//            SendResult sendResult = producer.send(Collections.emptyList());
//            System.out.printf("%s%n", sendResult);
//        } catch (Exception e) {
//            log.info("send message failed. message:{}", msg, e);
//        }
//        producer.shutdown();
//    }
//
//
//    public void testTransactionProducer() {
////        TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
////
////
////        producer.send()
//    }
//
//    @Test
//    public void testConsumer() throws MQClientException, InterruptedException {
//
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test");
//        consumer.setNamesrvAddr("127.0.0.1:9876");
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        consumer.setMessageModel(MessageModel.CLUSTERING);
//        consumer.subscribe("test", "*");
//        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
//            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        });
//        consumer.start();
//        Thread.sleep(100000L);
//    }
//
//
//    class MyTransactionListenerImpl implements TransactionListener {
//
//        @Override
//        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//            return LocalTransactionState.UNKNOW;
//        }
//
//        @Override
//        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
//            return LocalTransactionState.COMMIT_MESSAGE;
//        }
//    }
//}
