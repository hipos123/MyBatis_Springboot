package com.yaoxj.autowire;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {


    public static void main(String[] args) {
/*        AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(MainConfig.class);

        String[] names = ctx.getBeanDefinitionNames();
        for (String string : names) {
            System.out.println(string+","+ctx.getBean(string));
        }
        InstantA instantA = (InstantA) ctx.getBean("instantA");*/

        Integer a=1000,b=1000;
        Integer c=100,d=100;

        System.out.println(a==b);
        System.out.println(a.equals(b));
        System.out.println(c==d);
//        instantA.myName();
//
//        System.out.println(instantA);
//        System.out.println(instantA.getInstantB());
//          InstantD instantD = ctx.getBean(InstantD.class);
//        InstantD instantD = (InstantD) ctx.getBean("instantD");
//        instantD.getName();
    }
}
