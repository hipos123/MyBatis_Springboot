package com.yaoxj.beancycle2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-07-14 17:57
 **/
@Configuration
public class BeanCycleConfig {

    @Bean(initMethod = "initUser", destroyMethod = "destroyUser")
    public UserBean getUserBean() {
        return new UserBean();
    }

    @Bean
    public MyBeanPostProcessor getMyBeanPostProcessor() {
        return new MyBeanPostProcessor();

    }
}
