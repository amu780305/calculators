package com.wowoohr.calculators.controller;


import com.wowoohr.calculators.service.ITCityService;
import com.wowoohr.core.common.core.result.ApiResponseT;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 城市表 前端控制器
 * </p>
 *
 * @author chenhaomu
 * @since 2022-05-06
 */
@RestController
@RequestMapping("/v1/calculators")
public class TCityController {
    @Resource
    ITCityService itCityService;

    @RequestMapping(value = "/getCityList", method = RequestMethod.POST)
    public ApiResponseT getCityListForApp() {
        return ApiResponseT.success(itCityService.getCityList());
    }

}
