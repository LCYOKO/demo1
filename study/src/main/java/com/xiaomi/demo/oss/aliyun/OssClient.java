package com.xiaomi.demo.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author liuchiyun
 * @Date 2024/6/18
 */

@Slf4j
public class OssClient {

    private final OssConfig ossConfig;

    private final OSS ossClient;
    //https://md-ossbucket.oss-cn-beijing.aliyuncs.com/QQ%E6%88%AA%E5%9B%BE20210609114525.png  host的格式为 bucketname.endpoint
    private final String defaultHost;

    //默认token过期时间5分钟
    private static final long DEFAULT_EXPIRE_MILLIS_SECOND = 5 * 60 * 1000;

    public OssClient(OssConfig ossConfig) {
        this.ossConfig = ossConfig;
        defaultHost = "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint();
        ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
    }

    public OssPolicyResult getPolicy() {
        Date expiration = getDefaultExpireDate();
        String preDir = ossConfig.getPreDir();
        // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
        PolicyConditions policyConditions = new PolicyConditions();
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, preDir);
        String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
        // 解析请求响应
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        // 政策信息
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        // 签名信息
        String postSignature = ossClient.calculatePostSignature(postPolicy);
        OssPolicyResult ossPolicyResult = new OssPolicyResult();
        ossPolicyResult.setAccessid(ossConfig.getAccessKeyId());
        ossPolicyResult.setPolicy(encodedPolicy);
        ossPolicyResult.setSignature(postSignature);
        ossPolicyResult.setDir(preDir);
        ossPolicyResult.setHost(defaultHost);
        ossPolicyResult.setExpire(expiration.getTime() / 1000);
        return ossPolicyResult;
    }

    /**
     * 文件路径
     *
     * @param filePath 文件相对路径
     * @return 外网访问全路径
     */
    public String getFullFilePath(String filePath) {
        try {
            Date expiration = new Date(System.currentTimeMillis() + ossConfig.getDownloadExpireTime() * 1000L);
            URL url = ossClient.generatePresignedUrl(ossConfig.getBucketName(), filePath, expiration);
            return url.toString();
        } catch (Exception ex) {
            log.error("get full file path failed. filePath:{}", filePath, ex);
            return null;
        }
    }

    private Date getDefaultExpireDate() {
        long expireEndTime = System.currentTimeMillis() + DEFAULT_EXPIRE_MILLIS_SECOND;
        return new Date(expireEndTime);
    }
}
