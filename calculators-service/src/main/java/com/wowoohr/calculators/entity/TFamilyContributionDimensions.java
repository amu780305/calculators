package com.wowoohr.calculators.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenhaomu
 * @since 2022-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TFamilyContributionDimensions对象", description="")
public class TFamilyContributionDimensions implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id（唯一约束）")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "维度id")
    private String dimensionsId;

    @ApiModelProperty(value = "维度名称")
    private String dimensionName;

    @ApiModelProperty(value = "范围下限，包括")
    private Float rangeDown;

    @ApiModelProperty(value = "范围上限，不包括")
    private Float rangeUp;

    @ApiModelProperty(value = "对应结果")
    private String result;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "建立时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "操作人")
    private String updatedBy;


}
