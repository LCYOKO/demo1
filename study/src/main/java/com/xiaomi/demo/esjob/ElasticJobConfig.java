package com.xiaomi.demo.esjob;

import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/2
 */
public class ElasticJobConfig {
    @Value("${elasticJob.serverLists:zk_host:2181}")
    private String serverLists;

    @Value("${elasticJob.namespace:elastic-job-demo}")
    private String namespace;

    public CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverLists, namespace));
        regCenter.init();
        return regCenter;
    }
}
