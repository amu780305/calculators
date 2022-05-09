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
 * 城市表
 * </p>
 *
 * @author chenhaomu
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TCity对象", description="城市表")
public class TCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "帮助中心机器人地址")
    private String helpUrl;

    @ApiModelProperty(value = "公积金政策帮助中心机器人地址")
    private String gjjZcHelpUrl;

    @ApiModelProperty(value = "是否开启认证(1：认证开启 0：认证关闭)")
    private Integer isOpenAuth;

    @ApiModelProperty(value = "0:未开启  1：开启")
    private Integer isOpenCalc;

    @ApiModelProperty(value = "养老金计算器开启状态:0:未开启  1：开启")
    private Integer isOpenPensionCalc;

    @ApiModelProperty(value = "状态(1：有效，0：无效，3：删除)")
    private Integer status;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private String createdBy;

    private String updatedBy;

    @ApiModelProperty(value = "城市拼音")
    private String spell;

    @ApiModelProperty(value = "城市简称")
    private String shortSpell;


}
