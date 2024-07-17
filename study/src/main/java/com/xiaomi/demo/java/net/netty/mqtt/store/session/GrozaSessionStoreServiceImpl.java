package com.xiaomi.demo.java.net.netty.mqtt.store.session;

import com.xiaomi.demo.java.net.netty.mqtt.common.session.GrozaSessionStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.session.SessionStore;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：liuchiyun
 */
@Service
public class GrozaSessionStoreServiceImpl implements GrozaSessionStoreService {

    private Map<String, SessionStore> sessionCache = new ConcurrentHashMap<>();

    @Override
    public void put(String clientId, SessionStore sessionStore) {
        sessionCache.put(clientId, sessionStore);
    }

    @Override
    public SessionStore get(String clientId) {
        return sessionCache.get(clientId);
    }

    @Override
    public boolean containsKey(String clientId) {
        return sessionCache.containsKey(clientId);
    }

    @Override
    public void remove(String clientId) {
        sessionCache.remove(clientId);
    }


}
