package com.joy.autowire;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(MainConfig.class);

        String[] names = ctx.getBeanDefinitionNames();
        for (String string : names) {
            System.out.println(string+","+ctx.getBean(string));
        }
        InstantA instantA = (InstantA) ctx.getBean("instantA");
//        instantA.myName();
//
//        System.out.println(instantA);
//        System.out.println(instantA.getInstantB());
//          InstantD instantD = ctx.getBean(InstantD.class);
//        InstantD instantD = (InstantD) ctx.getBean("instantD");
//        instantD.getName();
    }
}
