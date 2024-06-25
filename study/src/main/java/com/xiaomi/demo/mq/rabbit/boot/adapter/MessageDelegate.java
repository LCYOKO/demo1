package com.xiaomi.demo.mq.rabbit.boot.adapter;

import com.rabbitmq.client.Channel;
import com.xiaomi.demo.mq.rabbit.boot.entity.Order;
import com.xiaomi.demo.mq.rabbit.boot.entity.Packaged;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Admin
 */
@Slf4j
public class MessageDelegate {

    public void handleMessage(byte[] messageBody) {
        System.err.println("默认方法, 消息内容:" + new String(messageBody));
    }

    public void consumeMessage(byte[] messageBody) {
        System.err.println("字节数组方法, 消息内容:" + new String(messageBody));
    }

    public void consumeMessage(String messageBody) {
        System.err.println("字符串方法, 消息内容:" + messageBody);
    }

    public void consumeMessage(Order order) {
        log.info("order:{}", order);
    }

    public void consumeMessage(Packaged pack) {
        System.err.println("package对象, 消息内容, id: " + pack.getId() +
                ", name: " + pack.getName() +
                ", content: " + pack.getDescription());
    }
}
