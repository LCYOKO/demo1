package com.xiaomi.demo.rule.liteflow;

import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/15
 */
@Component("c")
@Slf4j
public class ComponentC extends NodeComponent {
    @Override
    public void process() throws Exception {
        log.info("start process C");
    }
}
