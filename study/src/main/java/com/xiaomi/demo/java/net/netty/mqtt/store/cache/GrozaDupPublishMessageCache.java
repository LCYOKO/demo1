package com.xiaomi.demo.java.net.netty.mqtt.store.cache;

import com.xiaomi.demo.java.net.netty.mqtt.common.message.DupPublishMessageStore;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuchiyun
 */
@Service
public class GrozaDupPublishMessageCache {
    private final static String CACHE_PRE = "groza:publish:";

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    public DupPublishMessageStore put(String clientId, Integer messageId, DupPublishMessageStore dupPublishMessageStore) {
        redisCacheTemplate.opsForHash().put(CACHE_PRE + clientId, String.valueOf(messageId), dupPublishMessageStore);
        return dupPublishMessageStore;
    }

    public ConcurrentHashMap<Integer, DupPublishMessageStore> get(String clientId) {
        ConcurrentHashMap<Integer, DupPublishMessageStore> map = new ConcurrentHashMap<>();
        Map<Object, Object> map1 = redisCacheTemplate.opsForHash().entries(CACHE_PRE + clientId);
        if (MapUtils.isNotEmpty(map1)) {
            map1.forEach((k, v) -> map.put((Integer) k, (DupPublishMessageStore) v));
        }
        return map;
    }

    public boolean containsKey(String clientId) {
        return redisCacheTemplate.hasKey(CACHE_PRE + clientId);
    }

    public void remove(String clientId, Integer messageId) {
        redisCacheTemplate.opsForHash().delete(CACHE_PRE + clientId, messageId);
    }

    public void remove(String clientId) {
        redisCacheTemplate.delete(CACHE_PRE + clientId);
    }

}
