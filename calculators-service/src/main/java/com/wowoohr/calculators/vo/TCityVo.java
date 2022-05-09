package com.wowoohr.calculators.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@ApiModel(value="TCityVo对象", description="城市表")
public class TCityVo implements Serializable {

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

}
