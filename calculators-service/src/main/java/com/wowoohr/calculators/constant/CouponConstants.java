package com.wowoohr.calculators.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 卡劵系统常量类
 */
public class CouponConstants {

    // 券码状态  0：待领取；1：已领取；2：使用中；3：已使用；4：已作废、 5：未开始 、6:已过期  7：已退款
    public static final Short COUPON_STATUS_DLQ = 0;
    public static final Short COUPON_STATUS_YLQ = 1;
    public static final Short COUPON_STATUS_SYZ = 2;
    public static final Short COUPON_STATUS_YSY = 3;
    public static final Short COUPON_STATUS_ZF = 4;
    public static final Short COUPON_STATUS_WKS = 5;
    public static final Short COUPON_STATUS_GQ = 6;
    public static final Short COUPON_STATUS_YTK = 7;


    public static Map<Short,String> COUPON_STATUS_MAP = new LinkedHashMap<>();
    static {
        COUPON_STATUS_MAP.put(COUPON_STATUS_DLQ,"待领取");
        COUPON_STATUS_MAP.put(COUPON_STATUS_YLQ,"已领取");
        COUPON_STATUS_MAP.put(COUPON_STATUS_SYZ,"使用中");
        COUPON_STATUS_MAP.put(COUPON_STATUS_YSY,"已使用");
        COUPON_STATUS_MAP.put(COUPON_STATUS_ZF,"已作废");
        COUPON_STATUS_MAP.put(COUPON_STATUS_WKS,"未开始");
        COUPON_STATUS_MAP.put(COUPON_STATUS_GQ,"已过期");
        COUPON_STATUS_MAP.put(COUPON_STATUS_YTK,"已退款");
    }

}
