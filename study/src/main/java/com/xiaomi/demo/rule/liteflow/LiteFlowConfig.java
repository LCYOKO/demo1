package com.xiaomi.demo.rule.liteflow;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.spi.spring.SpringAware;
import com.yomahub.liteflow.spring.ComponentScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/15
 */
@Configuration
public class LiteFlowConfig {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SpringAware springAware() {
        return new SpringAware();
    }

    @Bean
    public LiteflowConfig liteflowConfig(SpringAware springAware) {
        LiteflowConfig config = new LiteflowConfig();
        config.setRuleSource("liteflow/flow.el.xml");
        config.setPrintBanner(false);
        return config;
    }

    @Bean
    public ComponentScanner componentScanner(LiteflowConfig liteflowConfig) {
        return new ComponentScanner(liteflowConfig);
    }

    @Bean
    public FlowExecutor flowExecutor(LiteflowConfig liteflowConfig) {
        FlowExecutor flowExecutor = new FlowExecutor();
        flowExecutor.setLiteflowConfig(liteflowConfig);
        return flowExecutor;
    }
}
