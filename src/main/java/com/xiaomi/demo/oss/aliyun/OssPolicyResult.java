package com.xiaomi.demo.oss.aliyun;

import lombok.Data;

/**
 * @Author: liuchiyun
 * @Date: 2023/8/5
 */
@Data
public class OssPolicyResult {
    private String accessid;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private Long expire;
}
