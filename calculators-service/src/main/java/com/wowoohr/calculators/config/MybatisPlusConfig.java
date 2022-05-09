package com.wowoohr.calculators.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.wowoohr.core.common.start.dataSource.source.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenhui
 * @version MyBatiesPlusConfiguration: MyBatiesPlusConfiguration.java, v 0.1 2021年04月21日 下午3:18 chenhui Exp $
 */
@Configuration
@MapperScan("com.wowoohr.calculators.mapper")
@Primary
public class MybatisPlusConfig {


    @Bean(name = "my-baseservice")
    @ConfigurationProperties(prefix = "spring.datasource.druid.my-baseservice" )
    public DataSource myBaseservice() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "family-contribution")
    @ConfigurationProperties(prefix = "spring.datasource.druid.family-contribution" )
    public DataSource familyContribution() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 动态数据源配置
     * @return
     */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("my-baseservice") DataSource myBaseserviceDataSource,
                                         @Qualifier("family-contribution") DataSource familyContributionDataSource ) {
        DynamicDataSource multipleDataSource = new DynamicDataSource();
        Map< Object, Object > targetDataSources = new HashMap<>();
        targetDataSources.put("my-baseservice", myBaseserviceDataSource);
        targetDataSources.put("family-contribution", familyContributionDataSource);
        //添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(myBaseserviceDataSource);
        return multipleDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(myBaseservice(),familyContribution()));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
//        configuration.setLogImpl(StdOutImpl.class);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/calculators/*"));
        sqlSessionFactory.setConfiguration(configuration);

        return sqlSessionFactory.getObject();

    }

}
