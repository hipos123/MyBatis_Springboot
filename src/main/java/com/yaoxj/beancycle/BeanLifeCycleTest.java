package com.yaoxj.beancycle;


import com.yaoxj.beancycle.factorybean.FactoryBeanService;

import com.yaoxj.beancycle.proxyfacrotybean.MyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanLifeCycleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext=new AnnotationConfigApplicationContext(LifeCycleConfig.class);
//		System.out.println(applicationContext.getBean("beanLifeCycleyaoxj"));
		BeanLifeCycle beanLifeCycle = (BeanLifeCycle) applicationContext.getBean("beanLifeCycle");
		System.out.println(beanLifeCycle.getBeanName());
		MyBean mybean = (MyBean) applicationContext.getBean("mybean");
		mybean.getMyName();

//		MyFactoryBean myFactoryBean = (MyFactoryBean) applicationContext.getBean("myFactoryBean");
//		myFactoryBean.setInterfaceName();

//		String[] names = applicationContext.getBeanDefinitionNames();
//		for (String string : names) {
//			System.out.println(string+","+applicationContext.getBean(string));
//		}

//		DemoFactoryBean demoFactoryBean= (DemoFactoryBean) applicationContext.getBean("demoFactoryBean");
//		Object object = demoFactoryBean.getObject();
//
//		System.out.println();
		FactoryBeanService bean = applicationContext.getBean(FactoryBeanService.class);
		bean.testFactoryBean();


	}

}
