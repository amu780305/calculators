package com.wowoohr.calculators.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wowoohr.calculators.datasource.DataSource;
import com.wowoohr.calculators.entity.TFamilyContributionCopys;
import com.wowoohr.calculators.entity.TFamilyContributionDimensions;
import com.wowoohr.calculators.entity.TFamilyContributionUserResult;
import com.wowoohr.calculators.enums.*;
import com.wowoohr.calculators.form.ChildForm;
import com.wowoohr.calculators.form.FamilyContributionCalculateForm;
import com.wowoohr.calculators.mapper.TFamilyContributionCopysMapper;
import com.wowoohr.calculators.mapper.TFamilyContributionDimensionsMapper;
import com.wowoohr.calculators.mapper.TFamilyContributionUserResultMapper;
import com.wowoohr.calculators.service.ITFamilyContributionService;
import com.wowoohr.calculators.vo.TFamilyContributionResult;
import com.wowoohr.core.common.core.enums.SystemCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenhaomu
 * @since 2022-05-07
 */
@Service
public class TFamilyContributionServiceImpl implements ITFamilyContributionService {
    private final Logger LOG = LogManager.getLogger(this.getClass());
    @Resource
    TFamilyContributionDimensionsMapper tFamilyContributionDimensionsMapper;

    @Resource
    TFamilyContributionCopysMapper tFamilyContributionCopysMapper;

    @Resource
    TFamilyContributionUserResultMapper tFamilyContributionUserResultMapper;

    @Override
    @DataSource("family-contribution")
    public TFamilyContributionResult getFamilyContributionResult(FamilyContributionCalculateForm form) {
        //TODO 增加BIZ层处理
        //获取城市等级
        float cityRate = CityGradeEnum.getCityGradeRate(form.getCityCode()).getRate();
        final float[] marketSalary = {0};
        float contribution = 0;
        //child care
        if (ObjectUtil.isNotEmpty(form.getChildren())) {
            List<ChildForm> childFormList = form.getChildren();
            childFormList.stream().forEach(childForm -> {
                marketSalary[0] += KidCareEnum.getKidRate(childForm.getAge()).getRate() * cityRate;
                if (ObjectUtil.isNotEmpty(childForm.getCounseling())) {
                    marketSalary[0] += KidCareEnum.getKidRate(childForm.getCounseling()).getRate() * cityRate;
                }
            });
        }
        //oldmen care
        if (ObjectUtil.isNotEmpty(form.getOldmen())) {
            marketSalary[0] += OldmanCareEnum.getOldmanRate(form.getOldmen()).getRate() * cityRate;
        }
        //cook
        if (ObjectUtil.isNotEmpty(form.getCook())) {
            marketSalary[0] += CookEnum.getCookRate(form.getCook()).getRate() * cityRate;
        }
        //housewok
        if (ObjectUtil.isNotEmpty(form.getHouseworks())) {
            List<String> houseworkList = form.getHouseworks();
            houseworkList.stream().forEach(string -> {
                marketSalary[0] += HouseWorkEnum.getWorkRate(string).getRate() * cityRate;
            });
        }
        //pet care
        if (ObjectUtil.isNotEmpty(form.getPetCare())) {
            marketSalary[0] += PetCareEnum.getPetRate(form.getPetCare()).getRate() * cityRate;
        }
        if (form.getIncome() != 0f) {
            contribution = marketSalary[0] + form.getIncome();
        }
        //五个维度星值查询
        List<TFamilyContributionDimensions> tFamilyContributionDimensionsList = tFamilyContributionDimensionsMapper.selectList(
                new QueryWrapper<TFamilyContributionDimensions>()
                        .ge("range_down", contribution)
                        .lt("range_up", contribution)
        );
        int patient = 0;
        int filialPiety = 0;
        int diligent = 0;
        int compassion = 0;
        String patientStars = "";
        String filialPietyStars = "";
        String diligentStars = "";
        String compassionStars = "";
        //patient
        if (tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("patient")).findFirst().isPresent()) {
            patient = StarsEnum.getStarRate(tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("patient")).findFirst().get().getResult()).getRate();
            patientStars = tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("patient")).findFirst().get().getResult();
        }
        //filialPiety
        if (tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("filialPiety")).findFirst().isPresent()) {
            filialPiety = StarsEnum.getStarRate(tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("filialPiety")).findFirst().get().getResult()).getRate();
            filialPietyStars = tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("filialPiety")).findFirst().get().getResult();
        }
        //diligent
        if (tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("diligent")).findFirst().isPresent()) {
            diligent = StarsEnum.getStarRate(tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("diligent")).findFirst().get().getResult()).getRate();
            diligentStars = tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("diligentStars")).findFirst().get().getResult();

        }
        //compassion
        if (tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("compassion")).findFirst().isPresent()) {
            compassion = StarsEnum.getStarRate(tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("compassion")).findFirst().get().getResult()).getRate();
            compassionStars = tFamilyContributionDimensionsList.stream().filter(e -> e.getDimensionName().equals("compassion")).findFirst().get().getResult();

        }
        //总产能,TODO 后期可考虑优化
        int total = patient + filialPiety + diligent + compassion;
        String totalStar = StarsEnum.getStarName(total / 4 + (total % 4 != 0 ? 1 : 0)).getStar();

        //超过百分比，
        //TODO 后续表数据量过大需要重新考虑优化方案
        int num = tFamilyContributionUserResultMapper.selectCount(
                new QueryWrapper<TFamilyContributionUserResult>()
                        .gt("contribution", contribution));
        int numCount = tFamilyContributionUserResultMapper.selectCount(
                new QueryWrapper<TFamilyContributionUserResult>());

        float rate = 0;
        if (numCount == 0) {
            rate = 0f;
        } else {
            rate = (float) (Math.round(((float) num / (float) numCount) * 100)) / 100;
        }
        //获取文案
        int standard = rate < 0.1f ? 1 : 2;
        if (standard == 2) {
            standard = rate > 0.9f ? 3 : 2;
        }
        List<TFamilyContributionCopys> tFamilyContributionCopysList = tFamilyContributionCopysMapper.selectList(
                new QueryWrapper<TFamilyContributionCopys>()
                        .eq("standard", standard)
        );
        int size = tFamilyContributionCopysList.size();

        //随机选择文案
        int idRandom = (int) (0 + Math.random() * (size));
        TFamilyContributionResult tFamilyContributionResult = new TFamilyContributionResult();
        tFamilyContributionResult.setMarketingPrice(Math.round(marketSalary[0]));
        tFamilyContributionResult.setContribution(Math.round(contribution));
        tFamilyContributionResult.setPatient(patientStars);
        tFamilyContributionResult.setFilialPiety(filialPietyStars);
        tFamilyContributionResult.setDiligent(diligentStars);
        tFamilyContributionResult.setCompassion(compassionStars);
        tFamilyContributionResult.setTotal(totalStar);
        tFamilyContributionResult.setCopy(tFamilyContributionCopysList.get(idRandom).getCopyContent());
        tFamilyContributionResult.setRate(rate * 100 + "%");
        //save
        TFamilyContributionUserResult tFamilyContributionUserResult = new TFamilyContributionUserResult();
        tFamilyContributionUserResult.setUserId(form.getPlatformId());
        tFamilyContributionUserResult.setCreatedBy(form.getPlatformId());
        tFamilyContributionUserResult.setCreatedTime(new Date());
        tFamilyContributionUserResult.setPlateform(form.getPlatformType());
        tFamilyContributionUserResult.setMarketSalary(Math.round(marketSalary[0]));
        tFamilyContributionUserResult.setContribution(Math.round(marketSalary[0] + form.getIncome()));
        tFamilyContributionUserResult.setResultId(tFamilyContributionCopysList.get(idRandom).getCopyId());
        int ret = tFamilyContributionUserResultMapper.insert(tFamilyContributionUserResult);
        if (ret != 1) {
            LOG.error(SystemCode.SERVICE_MSG.getCode(), "保存用户记录失败={}");
        }

        return tFamilyContributionResult;
    }

}
