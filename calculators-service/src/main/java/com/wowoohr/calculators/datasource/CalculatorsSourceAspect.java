package com.wowoohr.calculators.datasource;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author LST
 * @version 1.0
 * @Description: AOP实现的数据源切换
 * @date 2020-1-6 16:22
 */
@Component
@Aspect
@Order(-101)//这是为了保证AOP在事务注解之前生效,Order的值越小,优先级越高
public class CalculatorsSourceAspect {

    @Pointcut("@within(com.wowoohr.calculators.datasource.DataSource) || @annotation(com.wowoohr.calculators.datasource.DataSource)")
    public void pointcut() {
    }

    @Before("pointcut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource) {
        DataSourceContextHolder.setDataSource(dataSource.value());
    }

    @After("pointcut()")
    public void jarvisWxDb() {
        DataSourceContextHolder.clear();
    }
}
