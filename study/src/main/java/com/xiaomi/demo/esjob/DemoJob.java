package com.xiaomi.demo.esjob;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/4
 */
@Slf4j
public class DemoJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("DemoJob execute, context:{}", shardingContext);
    }
}
