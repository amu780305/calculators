package com.wowoohr.calculators.form.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luolihua
 */
@Data
public class CommitIdcardForm extends BaseAuthForm {

    @ApiModelProperty("真实姓名（auth_status=1时可信 ）")
    String realName;

    @ApiModelProperty("身份证号（auth_status=1时可信 ）")
    String certNo;

    @ApiModelProperty("身份证正面（auth_status=1时可信 ）")
    String idCardPhotoFront;

    @ApiModelProperty("身份证正面（auth_status=1时可信 ）")
    String idCardPhotoBack;

    @ApiModelProperty("商户业务页面回调的目标地址")
    String returnUrl;


}