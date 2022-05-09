package com.wowoohr.calculators.config.aliyun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aliyun")
@RefreshScope
public class AliyunConfig {
    /**
     * 数据中心地址
     */
    private String endpoint;

    /**
     * 文件仓储名
     */
    private String bucketName;


    private String accessKeyId;

    private String accessKeySecret;

    /**
     * ESC对称加密密钥
     */
    private String ESCSecret;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getESCSecret() {
        return ESCSecret;
    }

    public void setESCSecret(String ESCSecret) {
        this.ESCSecret = ESCSecret;
    }

    @Override
    public String toString() {
        return "AliyunConfig{" +
                "endpoint='" + endpoint + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", ESCSecret='" + ESCSecret + '\'' +
                '}';
    }
}
