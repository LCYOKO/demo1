package com.xiaomi.demo.java.net.netty.mqtt.common.subscribe;

import java.util.List;

/**
 * @author liuchiyun
 */
public interface GrozaSubscribeStoreService {
    /**
     * 存储订阅
     */
    void put(String topicFilter, SubscribeStore subscribeStore);

    /**
     * 删除订阅
     */
    void remove(String topicFilter, String clientId);

    /**
     * 删除clientId的订阅
     */
    void removeForClient(String clientId);

    /**
     * 获取订阅存储集
     */
    List<SubscribeStore> search(String topic);
}
