package com.yaoxj.autowire;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor  implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        // 1，InstantA instantA = (InstantA) ctx.getBean("instantA");  如果使用了这个增强器，那么在获取bean的时候，就会出错，类型不一样。
/*        GenericBeanDefinition rootBeanDefinition= (GenericBeanDefinition) beanFactory.getBeanDefinition("instantA");
        rootBeanDefinition.setBeanClass(InstantC.class);*/

        //2,修改注入模式 BeanFactoryPostProcessor 在bean还没实例化的时候，改变bean的属性
/*        GenericBeanDefinition rootBeanDefinition= (GenericBeanDefinition) beanFactory.getBeanDefinition("instantA");
        rootBeanDefinition.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR);*/
//        rootBeanDefinition.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE);
//        rootBeanDefinition.setBeanClass(InstantC.class);

/*      //3.修改构造函数
        GenericBeanDefinition rootBeanDefinition= (GenericBeanDefinition) beanFactory.getBeanDefinition("person");
        ConstructorArgumentValues constructorArgumentValues=new ConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0,"yaoxj");
        constructorArgumentValues.addIndexedArgumentValue(1,18);
        rootBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
*/


    }
}
