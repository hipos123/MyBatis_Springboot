package com.yaoxj.beancycle;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@PropertySource(value = {"classpath:beancycle.properties"})
//@ConditionalOnProperty(prefix = "beancycle")
@Component
@Data
public class BeanLifeCycle implements
        BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean
//		, ResourceLoaderAware
		 {

	private  String beanName;
	private Integer age;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}


/*private MyBean myBean;

	public MyBean getMyBean() {
		return myBean;
	}

	public void setMyBean(MyBean myBean) {
		System.out.println("set方法开始了吗");
		this.myBean = myBean;
	}*/

/*	static{
		System.out.println("static方法第一个");
	}*/
	public BeanLifeCycle(){
		System.out.println("这个是BeanLifeCycle的构造函数");
		System.out.println("设置年龄===="+age);
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
		this.beanName=name+"yaoxj";
		System.out.println("BeanNameAware的setBeanName方法==="+name+"&&&age==="+age);

	}

	@PostConstruct
	public void doPostConstruct(){
		System.out.println("@PostConstruct= 构造方法==");
	}




}
