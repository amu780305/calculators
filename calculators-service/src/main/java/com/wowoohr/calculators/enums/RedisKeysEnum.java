package com.wowoohr.calculators.enums;

/**
 * Redis Key枚举定义
 * key定义规则，模块名:小模块:功能:自定义key
 * @author luolihua
 */
public enum RedisKeysEnum {

    USER_CAPTCHA("workplace:user:captcha:%s", "用户验证码"),
    OCR_FRONT_TIME("workplace:user:identity:ocr-front:%s", "用户身份证正面ocr次数"),
    OCR_BACK_TIME("workplace:user:identity:ocr-back:%s", "用户身份证反面ocr次数") ;

    String key;
    String remark;

    RedisKeysEnum(String key, String remark) {
        this.key = key;
        this.remark = remark;
    }

    public String build(String ...args) {
        return String.format(this.key, args);
    }


}
