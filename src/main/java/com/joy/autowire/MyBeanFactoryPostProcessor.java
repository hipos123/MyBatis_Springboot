package com.joy.autowire;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor  implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //BeanFactoryPostProcessor 在bean还没实例化的时候，改变bean的属性
//        GenericBeanDefinition rootBeanDefinition= (GenericBeanDefinition) beanFactory.getBeanDefinition("instantA");
//        rootBeanDefinition.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE);
//        rootBeanDefinition.setBeanClass(InstantC.class);

        GenericBeanDefinition rootBeanDefinition= (GenericBeanDefinition) beanFactory.getBeanDefinition("person");
        ConstructorArgumentValues constructorArgumentValues=new ConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0,"yaoxj");
        rootBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);


    }
}
