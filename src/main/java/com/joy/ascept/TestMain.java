package com.joy.ascept;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(MainConfig.class);
        InstantA instantA = (InstantA) ctx.getBean("instantA");
        System.out.println(instantA);
        System.out.println(instantA.getInstantB());
    }
}
