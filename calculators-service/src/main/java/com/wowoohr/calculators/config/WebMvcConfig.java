package com.wowoohr.calculators.config;

import com.wowoohr.core.common.core.config.DefaultWebMvcConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenhui
 * @version WebMvcConfig: WebMvcConfig.java, v 0.1 2021年08月18日 下午12:01 chenhui Exp $
 */
@Configuration
public class WebMvcConfig extends DefaultWebMvcConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Bean
    RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(host);
//        factory.setPort(port);
//        return factory;
//    }

//    @Bean
//    public JedisConnectionFactory redisConnectionFactory(JedisPoolConfig config) {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(host);
//        factory.setPort(port);
//        factory.setPoolConfig(config);
//        return factory;
//    }

}
