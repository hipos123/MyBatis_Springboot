package com.yaoxj.beancycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

import javax.annotation.PostConstruct;


public class BeanLifeCycle implements
        BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean {
	public BeanLifeCycle(){
		System.out.println("这个是BeanLifeCycle的构造函数");
	}

	public void init(){
		System.out.println("这个是BeanLifeCycle的init函数");
	}

	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPropertiesSet");

	}

/*	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("前置处理器方法==="+bean+"&&&beanName=="+beanName);
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("后置处理器方法==="+bean+"&&&beanName=="+beanName);
		return bean;
	}*/

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactoryAware的setBeanFactory方法==="+beanFactory);

	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("BeanClassLoaderAware扩展点的setBeanClassLoader方法==="+classLoader.toString());
	}

	public void setBeanName(String name) {
		// TODO Auto-generated method stubBeanNameAware

		System.out.println("BeanNameAware的setBeanName方法==="+name);

	}

	@PostConstruct
	public void doPostConstruct(){
		System.out.println("@PostConstruct= 构造方法==");
	}




}
