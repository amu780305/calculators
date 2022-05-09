package com.wowoohr.calculators.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 做家务枚举
 *
 */
@Getter
public enum HouseWorkEnum {
    /**
     * 做家务枚举
     */
    HOUSEWORK0(0,"noWork"),
    HOUSEWORK1(600,"sortingAndStorage"),
    HOUSEWORK2(800,"dailyCleaning"),
    HOUSEWORK3(1000,"laundry"),
    HOUSEWORK4(2000,"deepCleaning");
    float rate;
    String housewok;
    HouseWorkEnum(float rate, String housewok) {
        this.rate = rate;
        this.housewok = housewok;
    }

    public static HouseWorkEnum getWorkRate(String housework){
        return Arrays.stream(values()).filter(HouseWorkEnum -> Objects.equals(HouseWorkEnum.getHousewok(),housework)).findFirst().orElse(HOUSEWORK0);
    }


}
