package com.wowoohr.calculators.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wowoohr.calculators.entity.TCity;
import com.wowoohr.calculators.vo.TCityVo;

import java.util.List;

/**
 * <p>
 * 城市表 服务类
 * </p>
 *
 * @author chenhaomu
 * @since 2022-05-06
 */
public interface ITCityService extends IService<TCity> {

    List<TCityVo> getCityList();

}
