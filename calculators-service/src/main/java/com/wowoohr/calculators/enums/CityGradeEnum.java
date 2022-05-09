package com.wowoohr.calculators.enums;


import com.wowoohr.calculators.constant.CityRate;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 城市等级划分
 * @author luolihua
 */
@Getter
public enum CityGradeEnum {
    /**
     * 城市枚举,cityCode
     */
    CITY1(CityRate.level1,"上海","310100"),
    CITY2(CityRate.level1,"北京","110100"),
    CITY3(CityRate.level1,"深圳","440300"),
    CITY4(CityRate.level1,"广州","440100"),
    CITY5(CityRate.level2,"苏州","320500"),
    CITY6(CityRate.level2,"杭州","330100"),
    CITY7(CityRate.level2,"南京","320100"),
    CITY8(CityRate.level2,"宁波","330200"),
    CITY9(CityRate.level2,"厦门","350200"),
    CITY10(CityRate.level2,"无锡","320200"),
    CITY11(CityRate.level2,"绍兴","330600"),
    CITY12(CityRate.level2,"东莞","441900"),
    CITY13(CityRate.level2,"佛山","440600"),
    CITY14(CityRate.level2,"珠海","440400"),
    CITY15(CityRate.level2,"舟山","330900"),
    CITY16(CityRate.level2,"嘉兴","330400"),
    CITY17(CityRate.level2,"温州","330300"),
    CITY18(CityRate.level2,"中山","442000"),
    CITY19(CityRate.level3,"湖州","330500"),
    CITY20(CityRate.level3,"常州","320400"),
    CITY21(CityRate.level3,"金华","330700"),
    CITY22(CityRate.level3,"长沙","430100"),
    CITY23(CityRate.level3,"台州","331000"),
    CITY24(CityRate.level3,"克拉玛依","650200"),
    CITY25(CityRate.level3,"青岛","370200"),
    CITY26(CityRate.level3,"武汉","420100"),
    CITY27(CityRate.level3,"镇江","321100"),
    CITY28(CityRate.level3,"包头","150200"),
    CITY29(CityRate.level3,"乌海","150300"),
    CITY30(CityRate.level3,"天津","120100"),
    CITY31(CityRate.level4,"三沙","460300"),
    CITY32(CityRate.level4,"南通","320600"),
    CITY33(CityRate.level4,"济南","370100"),
    CITY34(CityRate.level4,"马鞍山","340500"),
    CITY35(CityRate.level4,"合肥","340100"),
    CITY36(CityRate.level4,"东营","370500"),
    CITY37(CityRate.level4,"成都","510100"),
    CITY38(CityRate.level4,"鄂尔多斯","150600"),
    CITY39(CityRate.level4,"沈阳","210100"),
    CITY40(CityRate.level4,"大连","210200"),
    CITY41(CityRate.level4,"乌鲁木齐","650100"),
    CITY42(CityRate.level4,"威海","371000"),
    CITY43(CityRate.level4,"泉州","350500"),
    CITY44(CityRate.level4,"泰州","321200"),
    CITY45(CityRate.level4,"惠州","441300"),
    CITY46(CityRate.level4,"南昌","360100"),
    CITY47(CityRate.level4,"衢州","330800"),
    CITY48(CityRate.level4,"烟台","370600"),
    CITY49(CityRate.level4,"福州","350100"),
    CITY50(CityRate.level4,"嘉峪关","620200"),
    CITY51(CityRate.level5,"其他","000000");
    float rate;
    String city;
    String cityCode;
    CityGradeEnum(float rate, String city,String cityCode) {
        this.rate = rate;
        this.city = city;
        this.cityCode = cityCode;
    }

    public static CityGradeEnum getCityGradeRate(String cityCode){
        return Arrays.stream(values()).filter(CityGradeEnum -> Objects.equals(CityGradeEnum.getCityCode(),cityCode)).findFirst().orElse(CITY51);
    }


}
