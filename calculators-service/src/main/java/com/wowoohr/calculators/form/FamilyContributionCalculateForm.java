package com.wowoohr.calculators.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class FamilyContributionCalculateForm extends BaseForm{

    @ApiModelProperty(value = "城市代码",name = "cityCode")
    @NotBlank(message = "cityCode不能为空")
    private String cityCode;

    @Valid
    @ApiModelProperty(value = "照顾小孩列表",name = "children")
    private List<ChildForm> children;

    @ApiModelProperty(value = "照顾老人数量",name = "oldmen")
    private String oldmen;

    @ApiModelProperty(value = "做饭次数",name = "cook")
    private String cook;

    @Valid
    @ApiModelProperty(value = "做家务",name = "housework")
    private List<String> houseworks;

    @ApiModelProperty(value = "养宠物",name = "petCare")
    private String petCare;

    @ApiModelProperty(value = "个人月收入",name = "income")
    private float income;



}
