package com.wowoohr.calculators.controller;


import com.wowoohr.core.common.core.result.ApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/calculators")
public class CheckStartController {

    @RequestMapping(value = "/checkStartingStatus", method = RequestMethod.GET)
    public ApiResponse checkStartingStatus() {
        return ApiResponse.success();
    }
}
