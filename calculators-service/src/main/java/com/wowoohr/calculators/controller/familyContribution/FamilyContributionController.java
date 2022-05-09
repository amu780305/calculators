package com.wowoohr.calculators.controller.familyContribution;


import com.alibaba.fastjson.JSONObject;
import com.wowoohr.calculators.common.domain.HeaderEnum;
import com.wowoohr.calculators.form.FamilyContributionCalculateForm;
import com.wowoohr.calculators.service.ITFamilyContributionService;
import com.wowoohr.core.common.core.annotation.UserAccess;
import com.wowoohr.core.common.core.error.ServiceException;
import com.wowoohr.core.common.core.result.ApiResponseT;
import com.wowoohr.core.common.core.user.WowooUserInfoContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenhaomu
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/calculators/familyContribution")
public class FamilyContributionController {
    private final Logger LOG = LogManager.getLogger(this.getClass());
    @Resource
    ITFamilyContributionService itFamilyContributionService;

    @RequestMapping(value = "/calculateFamilyContribution", method = RequestMethod.POST)
    public ApiResponseT calculateFamilyContribution(@UserAccess WowooUserInfoContainer token, @Validated @RequestBody FamilyContributionCalculateForm form) {
        LOG.info("calculateFamilyContribution，FamilyContributionCalculateForm:{}", JSONObject.toJSONString(form));

        if (token == null) {
            throw new ServiceException("token shouldn't be null");
        }
        if (token.getThirdUnionid() != null) {
            form.setPlatformId(token.getThirdUnionid());
            form.setPlatformType(token.getThirdPlatform());
        } else {
            return ApiResponseT.error(HeaderEnum.UNKNOW_ERROR.getCode(), "token is null!");
        }
        return ApiResponseT.success(itFamilyContributionService.getFamilyContributionResult(form));
    }

}
