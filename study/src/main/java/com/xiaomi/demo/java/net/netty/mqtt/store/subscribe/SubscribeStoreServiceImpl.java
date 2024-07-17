package com.xiaomi.demo.java.net.netty.mqtt.store.subscribe;

import cn.hutool.core.util.StrUtil;
import com.xiaomi.demo.java.net.netty.mqtt.common.subscribe.GrozaSubscribeStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.subscribe.SubscribeStore;
import com.xiaomi.demo.java.net.netty.mqtt.store.cache.GrozaSubscribeNotWildcardCache;
import com.xiaomi.demo.java.net.netty.mqtt.store.cache.GrozaSubscribeWildcardCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author：liuchiyun
 */
@Service
public class SubscribeStoreServiceImpl implements GrozaSubscribeStoreService {
    @Autowired
    private GrozaSubscribeWildcardCache grozaSubscribeWildcardCache;

    @Autowired
    private GrozaSubscribeNotWildcardCache grozaSubscribeNotWildcardCache;

    @Override
    public void put(String topicFilter, SubscribeStore subscribeStore) {
        if (StrUtil.contains(topicFilter,'#') || StrUtil.contains(topicFilter,'+')){
            grozaSubscribeWildcardCache.put(topicFilter,subscribeStore.getClientId(),subscribeStore);
        }else{
            grozaSubscribeNotWildcardCache.put(topicFilter,subscribeStore.getClientId(),subscribeStore);
        }
    }

    @Override
    public void remove(String topicFilter, String clientId) {
        if (StrUtil.contains(topicFilter,'#') || StrUtil.contains(topicFilter,'+')){
            grozaSubscribeWildcardCache.remove(topicFilter,clientId);
        }else {
            grozaSubscribeNotWildcardCache.remove(topicFilter,clientId);
        }
    }

    @Override
    public void removeForClient(String clientId) {
        grozaSubscribeNotWildcardCache.removeForClient(clientId);
        grozaSubscribeWildcardCache.removeForClient(clientId);
    }

    @Override
    public List<SubscribeStore> search(String topic) {
        List<SubscribeStore> subscribeStores = new ArrayList<>();
        List<SubscribeStore> list = grozaSubscribeNotWildcardCache.all(topic);
        if (list.size() > 0) {
            subscribeStores.addAll(list);
        }
        grozaSubscribeWildcardCache.all().forEach((topicFilter, map) -> {
            if (StrUtil.split(topic, '/').size() >= StrUtil.split(topicFilter, '/').size()) {
                List<String> splitTopics = StrUtil.split(topic, '/');//a
                List<String> spliteTopicFilters = StrUtil.split(topicFilter, '/');//#
                StringBuilder newTopicFilter = new StringBuilder();
                for (int i = 0; i < spliteTopicFilters.size(); i++) {
                    String value = spliteTopicFilters.get(i);
                    if ("+".equals(value)) {
                        newTopicFilter.append("+/");
                    } else if ("#".equals(value)) {
                        newTopicFilter.append("#/");
                        break;
                    } else {
                        newTopicFilter.append(splitTopics.get(i)).append("/");
                    }
                }
                newTopicFilter = new StringBuilder(StrUtil.removeSuffix(newTopicFilter.toString(), "/"));
                if (topicFilter.equals(newTopicFilter.toString())) {
                    Collection<SubscribeStore> collection = map.values();
                    List<SubscribeStore> list2 = new ArrayList<>(collection);
                    subscribeStores.addAll(list2);
                }
            }
        });
        return subscribeStores;
    }
}
