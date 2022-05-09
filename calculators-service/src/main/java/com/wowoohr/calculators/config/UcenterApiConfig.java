package com.wowoohr.calculators.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ucenter", ignoreInvalidFields = true)
@Data
public class UcenterApiConfig {

    /**
     * 短信开关
     */
    private Boolean smsSwitch;

    /**
     * 人力窝小程序appId
     */
    private String appId;
    private String userLoginOrRegisterUrl;
    private String userInfoUrl;
    private String accountTelCheckUrl;
    private String sendSmsCodeUrl;
    private String smsCodeCheckUrl;
    private String sendEmailUrl;
}