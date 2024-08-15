package com.xiaomi.demo.rule.liteflow;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/15
 */
@LiteflowComponent(id = "a")
@Slf4j
public class ComponentA extends NodeSwitchComponent {
    @Override
    public void process() throws Exception {
        OrderContext context = getContextBean(OrderContext.class);
        log.info("start process A, context:{}.", context);
    }

    @Override
    public String processSwitch() throws Exception {
        return null;
    }
}
