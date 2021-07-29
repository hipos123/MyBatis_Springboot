package com.yaoxj.beancycle;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanLifeCycleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext=new AnnotationConfigApplicationContext(LifeCycleConfig.class);
		System.out.println(applicationContext.getBean("beanLifeCycle"));
		MyBean mybean = (MyBean) applicationContext.getBean("mybean");
		mybean.getMyName();

	}

}
