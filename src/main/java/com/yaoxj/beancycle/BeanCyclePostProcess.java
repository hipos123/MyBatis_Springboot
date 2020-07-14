package com.yaoxj.beancycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-07-14 18:06
 **/
public class BeanCyclePostProcess implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("前置处理器方法==="+bean+"&&&beanName=="+beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("后置处理器方法==="+bean+"&&&beanName=="+beanName);
        return bean;
    }
}
