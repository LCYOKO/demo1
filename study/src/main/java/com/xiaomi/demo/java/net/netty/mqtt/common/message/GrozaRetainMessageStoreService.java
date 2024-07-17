package com.xiaomi.demo.java.net.netty.mqtt.common.message;

import java.util.List;

/**
 * @author liuchiyun
 */
public interface GrozaRetainMessageStoreService {
    /**
     * 存储retain标志消息
     */
    void put(String topic, RetainMessageStore retainMessageStore);

    /**
     * 获取retain消息
     */
    RetainMessageStore get(String topic);

    /**
     * 删除retain标志消息
     */
    void remove(String topic);

    /**
     * 判断指定topic的retain消息是否存在
     */
    boolean containsKey(String topic);

    /**
     * 获取retain消息集合
     */
    List<RetainMessageStore> search(String topicFilter);
}
