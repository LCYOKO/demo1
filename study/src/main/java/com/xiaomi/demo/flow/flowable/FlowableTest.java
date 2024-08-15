package com.xiaomi.demo.flow.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", "lisi");
        variables.put("nrOfHolidays", 2);
        variables.put("description", "请个假");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        log.info("Found {} tasks: {}", tasks.size(), tasks);
        log.info("variables:{}", taskService.getVariables(tasks.get(0).getId()));
    }
}
