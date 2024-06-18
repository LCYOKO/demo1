package com.xiaomi.demo.oss.aliyun;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 用于配置OSS的连接客户端
 *
 * @author yhdja
 */
@Data
public class OssConfig {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.endpoint.internal}")
    private String endpointInternal;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.preDir}")
    private String preDir;

    @Value("${aliyun.oss.download.expireTime}")
    private Integer downloadExpireTime;

}

