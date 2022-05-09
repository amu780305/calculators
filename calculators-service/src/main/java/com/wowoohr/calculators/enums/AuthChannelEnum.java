package com.wowoohr.calculators.enums;

import lombok.Getter;

/**
 * 认证渠道枚举
 * @author luolihua
 */
@Getter
public enum AuthChannelEnum {

    FACE("face", "face++渠道"),
    ALIYUN_AUTH("aliyun_auth", "阿里云实人认证渠道"),
    ;
    String code;
    String remark;

    AuthChannelEnum(String code, String remark) {
        this.code = code;
        this.remark = remark;
    }
}
