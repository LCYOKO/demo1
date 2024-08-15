package com.xiaomi.demo.rule.liteflow;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuchiyun
 * @Date: 2024/8/15
 * https://liteflow.cc/pages/v2.10.X/33833a/
 */
@RestController
@RequestMapping("/liteflow")
@Slf4j
public class LiteFlowController {
    @Autowired
    private FlowExecutor flowExecutor;

    @RequestMapping("/test")
    public String test() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        log.info("response:{}", response);
        return "success";
    }
}
