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
@ApiModel(value="TFamilyContributionCopys对象", description="")
public class TFamilyContributionCopys implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id（唯一约束）")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文案id")
    private String copyId;

    @ApiModelProperty(value = "文案内容")
    private String copyContent;

    @ApiModelProperty(value = "所对应评估结果")
    private String standard;

    @ApiModelProperty(value = "是否删除，0：正常；1:删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "建立时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "操作人")
    private String updatedBy;


}
