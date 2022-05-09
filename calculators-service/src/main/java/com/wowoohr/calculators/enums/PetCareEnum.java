package com.wowoohr.calculators.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 照顾宠物枚举
 *
 */
@Getter
public enum PetCareEnum {
    /**
     * 照顾宠物枚举
     */
    PET0(0,"noPet"),
    PET1(3000,"smallPet"),
    PET2(4500,"mediumPet"),
    PET3(6000,"largePet");


    float rate;
    String pet;
    PetCareEnum(float rate, String pet) {
        this.rate = rate;
        this.pet = pet;
    }

    public static PetCareEnum getPetRate(String pet){
        return Arrays.stream(values()).filter(PetCareEnum -> Objects.equals(PetCareEnum.getPet(),pet)).findFirst().orElse(PET0);
    }


}
