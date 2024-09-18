package com.xiaomi.demo.flow.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Map;

/**
 * @Author: liuchiyun
 * @Date: 2024/9/18
 */
@Slf4j
public class LogReject implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Map<String, Object> variables = execution.getVariables();
        log.info("reject. variables:{}", variables);
    }
}
