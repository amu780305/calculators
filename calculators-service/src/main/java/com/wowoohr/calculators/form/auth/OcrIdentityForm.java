package com.wowoohr.calculators.form.auth;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luolihua
 */
@Data
public class OcrIdentityForm extends BaseAuthForm {

    @JSONField(serialize = false)
    @ApiModelProperty(value = "图片base64", hidden = true)
    String imageBase64;

    @ApiModelProperty(value = "图片路径", hidden = true)
    String imagePath;

    @ApiModelProperty("图片类型 1:身份证正面, 2:身份证反面")
    Integer imageType;

}