package com.yaoxj.xunhuan;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-07-23 18:07
 **/
public class XunHuanBeanTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(XunHuanBeanConfig.class);
        AService aService = applicationContext.getBean("aService",AService.class);
        System.out.println(aService.aTest());
    }
}
