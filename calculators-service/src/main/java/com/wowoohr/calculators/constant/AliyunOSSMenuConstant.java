package com.wowoohr.calculators.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云OSS 常量参数
 */
public class AliyunOSSMenuConstant {

    private static Map<Short,String> aliyunOSSMenu;

    static {
        aliyunOSSMenu = new HashMap<>();
        // 广告目录
        aliyunOSSMenu.put((short)1,"advertising");
        aliyunOSSMenu.put((short)2,"Annual");
    }

    public static String getFileDir(Short menuType){
        String filedir = aliyunOSSMenu.get(menuType);

        if (filedir == null){
            filedir = "temporary" + menuType;
        }

        return filedir;
    }
}
