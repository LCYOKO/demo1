package com.xiaomi.demo.oss.minio;

import cn.hutool.core.io.FileUtil;
import com.xiaomi.common.http.Constants;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuchiyun
 * @Date: 2023/9/12
 * <p>
 * https://min.io/docs/minio/linux/developers/javascript/minio-javascript.html
 */
@Slf4j
public class YhMinioClient {

    private final MinioConfig minioConfig;

    private final MinioClient minioClient;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public YhMinioClient(MinioConfig minioConfig) {
        this.minioConfig = minioConfig;
        this.minioClient = MinioClient
                .builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getAccessSecret())
                .build();
    }

    /**
     * @param fileName 文件名
     * @return fullUrl 临时授权文件URL
     */
    public String getUploadUrl(String fileName) {
        return doGetPresignedObjectUrl(fileName, Method.PUT);
    }

    /**
     * 获取文件预览路径
     *
     * @param fileName 文件名
     * @return fullUrl 临时授权文件URL
     */
    public String getFullFilePath(String fileName) {
        return doGetPresignedObjectUrl(fileName, Method.GET);
    }

    /**
     * @param file           需要下载到本地的文件
     * @param sourceFileName 远端需要下载的文件名
     */
    public void downloadFile(File file, String sourceFileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String filePath = minioConfig.getPreDir() + sourceFileName;
        try (InputStream response = minioClient.getObject(GetObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(filePath)
                .build())) {
            FileUtil.writeFromStream(response, file);
        } catch (Exception ex) {
            log.error("download file:{} failed.", sourceFileName, ex);
        }
    }

    private String doGetPresignedObjectUrl(String fileName, Method method) {
        String filePath = minioConfig.getPreDir() + fileName;
        try {
            String presignedObjectUrl = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs(filePath, method));
            return presignedObjectUrl;
        } catch (Exception ex) {
            log.error("get full file path failed. filePath:{}, method:{}", filePath, method, ex);
            return null;
        }
    }

    private GetPresignedObjectUrlArgs getPresignedObjectUrlArgs(String objectName, Method method) {
        return GetPresignedObjectUrlArgs.builder()
                .method(method)
                .bucket(minioConfig.getBucketName())
                .object(objectName)
                .expiry(minioConfig.getDownloadExpireTime(), TimeUnit.SECONDS)
                .build();
    }


    private GetObjectArgs getObjectArgs(String ObjectName) {
        return GetObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(ObjectName)
                .build();
    }


    public InputStream getObjectFromOSS(String fileName) {
        String objectName = minioConfig.getPreDir() + fileName;
        try {
            return minioClient.getObject(getObjectArgs(objectName));
        } catch (Exception e) {
            log.error("get object error from MinIO, {}", fileName);
            return null;
        }
    }


    public String putObject(byte[] fileData, String fileName) {
        String conciseFileName = "generateDoc/" + fileName;
        String objectName = minioConfig.getPreDir() + conciseFileName;
        try {
            ObjectWriteResponse response = minioClient.putObject(getPutObjectArgs(fileData, objectName));
            return this.getFullFilePath(conciseFileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("put object error to MinIO, {}", objectName);
        }
        return "";
    }

    private PutObjectArgs getPutObjectArgs(byte[] fileData, String objectName) {
        return PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(objectName)
                .contentType(Constants.APPLICATION_OCTET_STREAM)
                .stream(new ByteArrayInputStream(fileData), -1, 10485760)
                .build();
    }


    public String uploadFile(byte[] bytes, String fileName) {
        String filePath = minioConfig.getPreDir() + fileName;
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(filePath)
                    .stream(inputStream, bytes.length, 5 * 1024 * 1024)
                    .build();
            ObjectWriteResponse response = minioClient.putObject(objectArgs);
            // 上传成功返回fileName, 否则返回null
            return response != null && StringUtils.isNotBlank(response.etag()) ? fileName : null;
        } catch (Exception e) {
            log.error("uploadFile fileName:{} error:", fileName, e);
            return null;
        }
    }

}
