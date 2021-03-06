package com.yaoxj.beancycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifeCycleConfig {
	
	@Bean(initMethod="init")
	public BeanLifeCycle beanLifeCycle(){
		return new BeanLifeCycle();
	}

	@Bean
	public BeanCyclePostProcess beanCyclePostProcess(){
		return new BeanCyclePostProcess();
	}
}
