package com.wowoohr.calculators.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 休息日类型枚举
 */
public enum RestdaTypeEnum {

    DOUBLE_CASE_DAY((short)1,"普通双休"),
    SINGLE_HOLIDAY((short)2,"普通单休"),
    CUSTOMIZE((short)3,"自定义");

    private Short code;

    private String desc;

    RestdaTypeEnum(Short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static List<Short> getRestdaTypeCodeList(){
        return Arrays.stream(values()).map(RestdaTypeEnum -> RestdaTypeEnum.getCode()).collect(Collectors.toList());
    }
}

