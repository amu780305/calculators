package com.wowoohr.calculators.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseForm {

    @ApiModelProperty(value = "平台类型",hidden = true)
    private String platformType;

    @ApiModelProperty(value = "平台id，分alipay，wechat两个平台",hidden = true)
    private String platformId;

}
