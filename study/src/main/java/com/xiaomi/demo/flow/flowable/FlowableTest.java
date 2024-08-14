package com.xiaomi.demo.flow.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/14
 * <p>
 * https://www.cnblogs.com/heyi-77/p/18204524
 * https://tkjohn.github.io/flowable-userguide/#getting.started.command.line
 */
@Slf4j
public class FlowableTest {

    @Test
    public void test1() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:33060/test")
                .setJdbcUsername("root")
                .setJdbcPassword("lcyoko123")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("flow/holiday-request.bpmn20.xml")
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        log.info("Found process definition: {}", processDefinition.getName());

    }
}
