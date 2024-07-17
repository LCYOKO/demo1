package com.xiaomi.demo.java.net.netty.mqtt.store.message;

import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaMessageIdService;
import org.springframework.stereotype.Service;

/**
 * @Author：liuchiyun
 */
@Service
public class MessageIdServiceImpl implements GrozaMessageIdService {
    @Override
    public int getNextMessageId() {
        return 0;
    }
}
