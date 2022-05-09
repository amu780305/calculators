package com.wowoohr.calculators.enums;

import lombok.Getter;

/**
 * 用户类型
 * @author luolihua
 */
@Getter
public enum UserLevelEnum {

    L1("1", "平台用户"),
    L2("2", "人力窝用户"),
    L3("3", "通过身份实人认证")
    ;
    String code;
    String remark;

    UserLevelEnum(String code, String remark) {
        this.code = code;
        this.remark = remark;
    }



}
