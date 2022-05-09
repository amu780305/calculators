package com.wowoohr.calculators.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 做饭枚举
 *
 */
@Getter
public enum CookEnum {
    /**
     * 做饭枚举
     */
    COOK0(0,"no"),
    COOK1(2400,"meal"),
    COOK2(4800,"twoMeals"),
    COOK3(7200,"threeMeals"),
    COOK4(9600,"fourMeals");
    float rate;
    String cook;
    CookEnum(float rate, String cook) {
        this.rate = rate;
        this.cook = cook;
    }

    public static CookEnum getCookRate(String cook){
        return Arrays.stream(values()).filter(CookEnum -> Objects.equals(CookEnum.getCook(),cook)).findFirst().orElse(COOK0);
    }


}
