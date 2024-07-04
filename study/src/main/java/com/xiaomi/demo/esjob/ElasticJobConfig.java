package com.xiaomi.demo.esjob;

import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/2
 * https://shardingsphere.apache.org/elasticjob/current/cn/user-manual/usage/job-api/java-api/
 */
public class ElasticJobConfig {
    @Value("${elasticJob.serverLists:localhost:2181}")
    private String serverLists;

    @Value("${elasticJob.namespace:elastic-job-demo}")
    private String namespace;

    public CoordinatorRegistryCenter coordinatorRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverLists, namespace));
        regCenter.init();
        return regCenter;
    }

//    public ScheduleJobBootstrap scheduleJobBootstrap(CoordinatorRegistryCenter coordinatorRegistryCenter) {
//        new ScheduleJobBootstrap(coordinatorRegistryCenter, new DemoJob(), "0/5 * * * * ?");
//    }
}
