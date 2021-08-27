package com.yaoxj.beancycle.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-13 16:54
 **/
@Component
public class FactoryBeanLearn implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new FactoryBeanServiceImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBeanService.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
