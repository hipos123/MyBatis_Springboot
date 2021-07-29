package com.yaoxj.xunhuan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-07-23 18:11
 **/
@ComponentScan("com.yaoxj.xunhuan")
@EnableAspectJAutoProxy
@Configuration
public class XunHuanBeanConfig {

    @Bean
    public  AService aService(){
        AService aService=new AService();
        return aService;
    }

    @Bean
    public  BService bService(){
        BService bService=new BService();
        return bService;
    }

    @Bean
    public  CService cService(){
        CService cService=new CService();
        return cService;
    }
}
