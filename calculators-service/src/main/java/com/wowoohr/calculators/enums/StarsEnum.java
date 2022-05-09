package com.wowoohr.calculators.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 星值枚举
 *
 */
@Getter
public enum StarsEnum {
    /**
     * 星值枚举
     */
    STAR0(0,"零颗星"),
    STAR1(1,"一颗星"),
    STAR2(2,"两颗星"),
    STAR3(3,"三颗星"),
    STAR4(4,"四颗星"),
    STAR5(5,"五颗星");
    int rate;
    String star;
    StarsEnum(int rate, String star) {
        this.rate = rate;
        this.star = star;
    }

    public static StarsEnum getStarRate(String star){
        return Arrays.stream(values()).filter(StarsEnum -> Objects.equals(StarsEnum.getStar(),star)).findFirst().orElse(STAR0);
    }

    public static StarsEnum getStarName(int rate){
        return Arrays.stream(values()).filter(StarsEnum -> Objects.equals(StarsEnum.getRate(),rate)).findFirst().orElse(STAR0);
    }

}
