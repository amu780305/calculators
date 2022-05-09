package com.wowoohr.calculators.enums;

/**
 * @author luolihua
 */
public enum AuthStatusEnum {
    NO("0","未认证"),
    YES("1","已认证");

    AuthStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
