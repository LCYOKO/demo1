package com.xiaomi.demo.distributed.oss.minio;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: liuchiyun
 * @Date: 2023/9/12
 */
@Data
public class MinioConfig {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.accessSecret}")
    private String accessSecret;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.preDir}")
    private String preDir;
    /**
     * 下载链接有效期（秒）
     */
    @Value("${minio.download.expireTime}")
    private Integer downloadExpireTime;
}
