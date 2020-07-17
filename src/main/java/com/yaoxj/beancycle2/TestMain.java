package com.yaoxj.beancycle2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-07-14 17:58
 **/
public class TestMain {

    public static void main(String[] args) {
        // 使用AnnotationConfigApplicationContext获取spring容器ApplicationContext2
        AnnotationConfigApplicationContext applicationContext2 = new AnnotationConfigApplicationContext(BeanCycleConfig.class);
        applicationContext2.close();

    }
}
