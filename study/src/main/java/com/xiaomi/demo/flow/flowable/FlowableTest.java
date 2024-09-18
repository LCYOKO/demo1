package com.xiaomi.demo.flow.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
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

    ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
            .setJdbcUrl("jdbc:mysql://localhost:33060/test")
            .setJdbcUsername("root")
            .setJdbcPassword("lcyoko123")
            .setJdbcDriver("com.mysql.cj.jdbc.Driver")
//            .setEventListeners()
            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    ProcessEngine processEngine = cfg.buildProcessEngine();

    @Test
    public void testCreateDeployment() {
        //Flowable的所有数据库表都以ACT_开头。第二部分是说明表用途的两字符标示符。服务API的命名也大略符合这个规则。
        //
        //ACT_RE_*: 'RE’代表repository。带有这个前缀的表包含“静态”信息，例如流程定义与流程资源（图片、规则等）。
        //
        //ACT_RU_*: 'RU’代表runtime。这些表存储运行时信息，例如流程实例（process instance）、用户任务（user task）、变量（variable）、作业（job）等。Flowable只在流程实例运行中保存运行时数据，并在流程实例结束时删除记录。这样保证运行时表小和快。
        //
        //ACT_HI_*: 'HI’代表history。这些表存储历史数据，例如已完成的流程实例、变量、任务等。
        //
        //ACT_GE_*: 通用数据。在多处使用。
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("flow/holiday-request.bpmn20.xml")
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        log.info("Found process definition: {}", processDefinition.getName());

    }

    @Test
    public void testStartFlow(){
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

    @Test
    public void testCompleteTask() {
        TaskService taskService = processEngine.getTaskService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", false);
        taskService.complete("27511", variables);
    }

    @Test
    public void testQuery() {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities =
                historyService.createHistoricActivityInstanceQuery()
                        .processDefinitionId("holidayRequest:6:27503")
                        .finished()
                        .orderByHistoricActivityInstanceEndTime().asc()
                        .list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " took "
                    + activity.getDurationInMillis() + " milliseconds");
        }
    }
}
