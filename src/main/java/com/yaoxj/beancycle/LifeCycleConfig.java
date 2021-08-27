package com.yaoxj.beancycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.yaoxj.beancycle")
public class LifeCycleConfig {
	
/*	@Bean(initMethod="init")
	public BeanLifeCycle beanLifeCycle(){
		System.out.println("这个是调用init方法吗");
		BeanLifeCycle beanLifeCycle = new BeanLifeCycle();
		beanLifeCycle.setAge(22);
		return beanLifeCycle;

	}*/

	@Bean
	public BeanCyclePostProcess beanCyclePostProcess(){
		return new BeanCyclePostProcess();
	}


	@Bean
	public MyBean mybean(){
		return new MyBean();
	}
}
