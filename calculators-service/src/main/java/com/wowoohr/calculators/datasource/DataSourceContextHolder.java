package com.wowoohr.calculators.datasource;

/**
 * @author LST
 * @version 1.0
 * @Description: 设置、获取、清除当前数据源的方法/并切换数据源
 * @date 2020-1-6 15:38
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

    /**
     *  设置数据源
     * @param db
     */
    public static void setDataSource(String db){
        contextHolder.set(db);
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDataSource(){
        return contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear(){
        contextHolder.remove();
    }

}
