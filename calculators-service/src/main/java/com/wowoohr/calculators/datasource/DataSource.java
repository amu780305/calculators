package com.wowoohr.calculators.datasource;
import java.lang.annotation.*;

/**
 * @author LST
 * @version 1.0
 * @Description: AOP选择数据源的时候做标识，即我们这里通过AOP读取到自定义的注解决定选择数据源
 * @date 2020-1-6 16:35
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default "my-baseservice";
}
