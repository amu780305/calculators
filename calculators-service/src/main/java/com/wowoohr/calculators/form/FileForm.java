package com.wowoohr.calculators.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 文件实体类
 */
@Data
public class FileForm {

    @ApiModelProperty(value = "图片key", name = "key")
    @NotBlank(message = "photo的key值不能为null")
    private String key;

    @ApiModelProperty(value = "图片url", name = "url")
    private String url;

}
