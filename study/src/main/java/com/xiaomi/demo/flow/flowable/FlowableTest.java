package com.xiaomi.demo.flow.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/14
 * <p>
 * https://www.cnblogs.com/heyi-77/p/18204524
 * https://tkjohn.github.io/flowable-userguide/#getting.started.command.line
 */
public class FlowableTest {

    @Test
    public void test1() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:33060")
                .setJdbcUsername("root")
                .setJdbcPassword("lcyoko")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();
    }
}
