package com.wowoohr.calculators.form;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AdvertisingForm {

    private Integer pageNum;
    private Integer pageSize;
    private String permit;  // 许可
    private String token;   // 登陆超时标记
    private String userId;

    // 广告位 uuid
    private String advertisingId;
    //广告内容 uuid
    private String advertisingContentId;
    //广告位名称
    private String siteName;
    //广告位状态
    private String siteStatus;
    //广告内容状态
    private String contentStatus;
    //数据来源 1 正式数据  2 二维码演示数据(因前端字段冲突，此字段删除)
    private String source;
    //数据来源 1 正式数据  2 二维码演示数据
    private String qrCodePreview;
    //城市code
    private String cityCode;

    //模块名称
    private String moduleName;
    //广告位名称
    private List<String> siteNameList;
    //广告内容名称
    private String advertisingName;

    private String appId;

    private String authCode;

    private  Long continueProductId;

    /**
     * 广告位尺寸(长)
     */
    @ApiModelProperty(value = "广告位尺寸(长)", name = "advertisingLength")
    private String advertisingLength;
    /**
     * 广告位尺寸(宽)
     */
    @ApiModelProperty(value = "广告位尺寸(宽)", name = "advertisingWidth")
    private String advertisingWidth;

    /**
     * 广告位内容标识（0:默认内容 1:运营活动内容）
     */
    private Integer advertisingFlag;

    /**
     * ABtest运营活动主键
     */
    private String operateActivityId;

}
