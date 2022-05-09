package com.wowoohr.calculators.utils;



import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

public class Base64Util {
    /***
     * Base64加密
     * @param str 需要加密的参数
     * @return
     * @throws Exception
     */
    public static String encrypt(String str) {
        String result = StringUtils.EMPTY;
        try {
            result = Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * Base64解密
     * @param str 需要解密的参数
     * @return
     * @throws Exception
     */
    public static String decrypt(String str){
        String result = StringUtils.EMPTY;
        try {
            byte[] asBytes = Base64.getDecoder().decode(str);
            result = new String(asBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
