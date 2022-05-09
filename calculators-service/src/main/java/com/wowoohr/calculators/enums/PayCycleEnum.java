package com.wowoohr.calculators.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 发薪周期枚举
 */
public enum PayCycleEnum {

    EVERY_WEEK((short)1,"每周"),
    EVERY_MONTH((short)2,"每月");

    private Short code;

    private String desc;

    PayCycleEnum(Short code, String desc) {
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

    public static List<Short> getPayCycleCodeList(){
        return Arrays.stream(values()).map(payCycleEnum -> payCycleEnum.getCode()).collect(Collectors.toList());
    }
}

