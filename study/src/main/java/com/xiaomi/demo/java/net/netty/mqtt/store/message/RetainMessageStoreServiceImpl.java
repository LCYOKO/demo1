package com.xiaomi.demo.java.net.netty.mqtt.store.message;

import cn.hutool.core.util.StrUtil;

import com.xiaomi.demo.java.net.netty.mqtt.common.message.GrozaRetainMessageStoreService;
import com.xiaomi.demo.java.net.netty.mqtt.common.message.RetainMessageStore;
import com.xiaomi.demo.java.net.netty.mqtt.store.cache.GrozaRetainMessageCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author：liuchiyun
 */
@Service
public class RetainMessageStoreServiceImpl implements GrozaRetainMessageStoreService {
    @Autowired
    private GrozaRetainMessageCache grozaRetainMessageCache;

    @Override
    public void put(String topic, RetainMessageStore retainMessageStore) {
        grozaRetainMessageCache.put(topic,retainMessageStore);
    }

    @Override
    public RetainMessageStore get(String topic) {
        return grozaRetainMessageCache.get(topic);
    }

    @Override
    public void remove(String topic) {
        grozaRetainMessageCache.remove(topic);
    }

    @Override
    public boolean containsKey(String topic) {
        return grozaRetainMessageCache.containsKey(topic);
    }

    @Override
    public List<RetainMessageStore> search(String topicFilter) {
        List<RetainMessageStore> retainMessageStores = new ArrayList<RetainMessageStore>();
        if (!StrUtil.contains(topicFilter, '#') && !StrUtil.contains(topicFilter, '+')) {
            if (grozaRetainMessageCache.containsKey(topicFilter)) {
                retainMessageStores.add(grozaRetainMessageCache.get(topicFilter));
            }
        } else {
            grozaRetainMessageCache.all().forEach((topic, val) -> {
                if (StrUtil.split(topic, '/').size() >= StrUtil.split(topicFilter, '/').size()) {
                    List<String> splitTopics = StrUtil.split(topic, '/');
                    List<String> spliteTopicFilters = StrUtil.split(topicFilter, '/');
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
                        retainMessageStores.add(val);
                    }
                }
            });
        }
        return retainMessageStores;
    }
}
