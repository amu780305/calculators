package com.wowoohr.calculators.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "careergreen")
@Data
public class CareerGreenMedalConfig {

    private Integer perserve;

    private String perserveimg;

    private Integer greenbit;

    private String greenbitimg;

    private Integer environmental;

    private String environmentalimg;

}
