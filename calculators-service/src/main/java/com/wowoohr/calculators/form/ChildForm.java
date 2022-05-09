package com.wowoohr.calculators.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 文件实体类
 */
@Data
public class ChildForm {

    @ApiModelProperty(value = "年龄", name = "age")
    @NotBlank(message = "age不能为null")
    private String age;

    @ApiModelProperty(value = "是否负责心理辅导", name = "counseling")
    private String counseling;

}
