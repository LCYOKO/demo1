package com.xiaomi.demo.mq.rabbit.boot;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/25
 */
@Slf4j
public class CustomerListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        log.info("received message:{}", message);
    }
}
