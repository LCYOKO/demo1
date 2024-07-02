package com.xiaomi.demo.xxl;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/26
 */
@Slf4j
public class DemonJob {

    @XxlJob("DemonJob")
    public void doJob(String jsonParam) {
    }
}
