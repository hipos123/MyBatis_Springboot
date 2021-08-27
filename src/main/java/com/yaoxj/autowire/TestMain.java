package com.yaoxj.autowire;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(MainConfig.class);
/*        InstantA instantA = (InstantA) ctx.getBean("instantA");
        System.out.println(instantA.getInstantB());*/


//
   /*     String[] names = ctx.getBeanDefinitionNames();
        for (String string : names) {
            System.out.println(string+","+ctx.getBean(string));
        }*/

        ImportSelectorInstant instantA = (ImportSelectorInstant) ctx.getBean("com.yaoxj.autowire.ImportSelectorInstant");
        System.out.println(instantA);


        ImportSelectorInstant instantA1 = (ImportSelectorInstant) ctx.getBean(ImportSelectorInstant.class);
        System.out.println(instantA1);

/*        ImportSelectorInstant instantA = (ImportSelectorInstant) ctx.getBean("ImportSelectorInstant");
        System.out.println(instantA);


        ImportBeanDefRegInstant myimportBeanDefRegInstant = (ImportBeanDefRegInstant) ctx.getBean("myimportBeanDefRegInstant");
        System.out.println(myimportBeanDefRegInstant);*/

//        Person person = (Person) ctx.getBean("person");
//        System.out.println("看看神奇的一幕吧：：：：："+person.getName()+"%%%%"+person.getAge());

  /*      String[] names = ctx.getBeanDefinitionNames();
        for (String string : names) {
            System.out.println(string+","+ctx.getBean(string));
        }*/
 /*       ImportSelectorInstant instantA = (ImportSelectorInstant) ctx.getBean("importSelectorInstant");
        System.out.println(instantA);*/
/*        Integer a=1000,b=1000;
        Integer c=100,d=100;

        System.out.println(a==b);
        System.out.println(a.equals(b));
        System.out.println(c==d);*/
//        instantA.myName();】=【-poi87u//
//        System.out.println(instantA);
//        System.out.println(instantA.getInstantB());
//          InstantD instantD = ctx.getBean(InstantD.class);
//        InstantD instantD = (InstantD) ctx.getBean("instantD");
//        instantD.getName();
    }
}
