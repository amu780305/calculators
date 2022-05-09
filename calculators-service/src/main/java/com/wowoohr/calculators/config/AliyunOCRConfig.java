package com.wowoohr.calculators.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by snowwolf-louis on 17/12/5.
 */
@Configuration
@ConfigurationProperties(prefix = "ocr.aliyun")
public class AliyunOCRConfig {
    private String host;
    private String path;
    private String appcode;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }
}
