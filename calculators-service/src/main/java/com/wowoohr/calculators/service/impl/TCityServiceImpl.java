package com.wowoohr.calculators.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wowoohr.calculators.entity.TCity;
import com.wowoohr.calculators.mapper.TCityMapper;
import com.wowoohr.calculators.service.ITCityService;
import com.wowoohr.calculators.vo.TCityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 城市表 服务实现类
 * </p>
 *
 * @author chenhaomu
 * @since 2022-05-06
 */
@Service
public class TCityServiceImpl extends ServiceImpl<TCityMapper, TCity> implements ITCityService {

    @Resource
    TCityMapper tCityMapper;

    @Override
    public List<TCityVo> getCityList() {
        List<TCity> tCities = tCityMapper.selectList(new QueryWrapper<TCity>()
                .eq("status", 1));
        List<TCityVo> tCityVos = new ArrayList<>();
        tCities.stream().forEach(tCity -> {
            TCityVo tCityVo = new TCityVo();
            BeanUtils.copyProperties(tCity, tCityVo);
            tCityVos.add(tCityVo);
        });
        return tCityVos;
    }
}
