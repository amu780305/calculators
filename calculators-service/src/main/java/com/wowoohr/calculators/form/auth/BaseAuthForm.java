package com.wowoohr.calculators.form.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luolihua
 */
@Data
public class BaseAuthForm implements Serializable {

    @ApiModelProperty(value = "使用渠道Code", hidden = true)
    String channelCode;

    @ApiModelProperty(value = "用户id", hidden = true)
    Long wowooId;

    @ApiModelProperty(value = "三方平台标识(ALIPAY,WEIXIN)", hidden = true)
    String thirdPlf;

    @ApiModelProperty(value = "三方平台用户id", hidden = true)
    String thirdUnionid;

}