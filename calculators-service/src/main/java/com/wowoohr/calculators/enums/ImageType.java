package com.wowoohr.calculators.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 罗利华
 */
@Getter
@AllArgsConstructor
public enum ImageType {

    IDCARD_FRONT(1, "身份证正面"),
    IDCARD_BACK(2, "身份证反面"),

    ;

    private Integer code;
    private String msg;

    public static ImageType get(Integer code) {
        return Arrays.stream(values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }

}
