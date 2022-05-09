package com.wowoohr.calculators.constant;

public interface ServiceConstants {

    /**
     * 成功
     */
    String SUCCESS = "200";
    /**
     * 空格
     */
    String ONE_SPACE = " ";
    /**
     * 批次前缀 batch
     */
    String BATCH_PRE = "BH";

    char ZERO = '0';

    String ZERO_STR = "0";

    short IS_DELETE_NO = 0;

    short IS_DELETE_YES = 1;

    short IS_OPERATIONAL_ACTIVITIES_NO = 0;

    short IS_OPERATIONAL_ACTIVITIES_YES = 1;

    short ADVERTISING_FLAG_NO = 0;

    short ADVERTISING_FLAG_YES = 1;

    short STATUS_NO = 0;

    short STATUS_YES = 1;

    /**
     * 推荐广告位缓存前缀
     */
    String RECOMMEND_ACTIVITY_PREFIX = "recommend:activity:";

    String RECOMMEND_CONTENT_PREFIX = "recommend:content:";

    String ACTIVITY = "ACTIVITY";

    String CONTENT = "CONTENT";

    /**
     * 灰度人员名单redis key
     */
    String GRAYSCALE_PERSONNELS = "GRAYSCALE_PERSONNELS";

    /**
     * 时间后缀 - 起始
     */
    String START_TIME_SUFFIX = " 00:00:00";
    /**
     * 时间后缀 - 结束
     */
    String END_TIME_SUFFIX = " 23:59:59";
    /**
     * 导出数据的最大记录
     */
    Integer IMPORT_MAX_NUMBER = 5000;
    /**
     * 逗号
     */
    String COMMA = ",";

    String NO_FLAG = "N";

    String YES_FLAG = "Y";

    String USERNAME = "username";
}
