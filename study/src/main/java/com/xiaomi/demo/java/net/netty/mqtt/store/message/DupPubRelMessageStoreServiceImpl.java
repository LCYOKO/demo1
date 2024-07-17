package com.xiaomi.demo.java.net.netty.mqtt.store.message;



import com.xiaomi.demo.java.net.netty.mqtt.common.message.DupPubRelMessageStore;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaDupPubRelMessageStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.store.cache.GrozaDupPubRelMessageCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：liuchiyun
 */
@Service
public class DupPubRelMessageStoreServiceImpl implements GrozaDupPubRelMessageStoreService {
    @Autowired
    private GrozaDupPubRelMessageCache grozaDupPubRelMessageCache;

    @Override
    public void put(String clientId, DupPubRelMessageStore dupPubRelMessageStore) {
        grozaDupPubRelMessageCache.put(clientId,dupPubRelMessageStore.getMessageId(),dupPubRelMessageStore);
    }

    @Override
    public List<DupPubRelMessageStore> get(String clientId) {
        if (grozaDupPubRelMessageCache.containsKey(clientId)){
            ConcurrentHashMap<Integer, DupPubRelMessageStore> map = grozaDupPubRelMessageCache.get(clientId);
            Collection<DupPubRelMessageStore> collection = map.values();
            return new ArrayList<>(collection);
        }
        return new ArrayList<>();
    }

    @Override
    public void remove(String clientId, int messageId) {
        grozaDupPubRelMessageCache.remove(clientId,messageId);
    }

    @Override
    public void removeByClient(String clientId) {
        if (grozaDupPubRelMessageCache.containsKey(clientId)){
            grozaDupPubRelMessageCache.remove(clientId);
        }
    }
}
