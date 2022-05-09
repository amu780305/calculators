package com.wowoohr.calculators.common;

public class RedisCacheKey {

    /**
     * 用户编辑职场日历锁
     */
    public static final String WORKPLACE_CAREERCALENDA_EDITMYCALENDA_LOCK = "workplace:careerCalenda:editMyCalenda:lock:{wowooId}";

    /**
     * 用户绿色职场打卡锁
     */
    public static final String WORKPLACE_CAREERGREEN_LOCK = "workplace:careerGreen:lock:{wowooId}";

}
