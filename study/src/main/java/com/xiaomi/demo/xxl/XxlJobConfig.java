package com.xiaomi.demo.xxl;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @Author: liuchiyun
 * @Date: 2024/7/2
 */
public class XxlJobConfig {
    @Value("${spring.xxl.appName}")
    private String appName;
    @Value("${spring.xxl.accessToken}")
    private String accessToken;
    @Value("${spring.xxl.adminAddresses}")
    private String adminAddresses;
    @Value("${spring.xxl.executorPort}")
    private int executorPort;
    @Value("${spring.xxl.logPath}")
    private String logPath;
    @Value("${spring.logRetentionDays}")
    private int logRetentionDays;

    @Bean
    public XxlJobSpringExecutor executor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAppname(appName);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setPort(executorPort);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobSpringExecutor;
    }
}
