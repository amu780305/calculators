package com.wowoohr.calculators.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 照顾孩子
 *
 */
@Getter
public enum KidCareEnum {
    /**
     * kid年龄
     */
    KID0(0,"noKid"),
    KID1(16000,"twoMonth"),
    KID2(12000,"preschoolAge"),
    KID3(4000,"educationPeriod"),
    KID4(12000,"psychologicalCounseling");
    float rate;
    String kid;
    KidCareEnum(float rate, String kid) {
        this.rate = rate;
        this.kid = kid;
    }

    public static KidCareEnum getKidRate(String kid){
        return Arrays.stream(values()).filter(KidCareEnum -> Objects.equals(KidCareEnum.getKid(),kid)).findFirst().orElse(KID0);
    }


}
