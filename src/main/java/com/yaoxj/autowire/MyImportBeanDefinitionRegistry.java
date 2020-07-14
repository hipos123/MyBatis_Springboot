package com.yaoxj.autowire;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-07-13 17:49
 **/
public class MyImportBeanDefinitionRegistry  implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition rootBeanDefinition=new RootBeanDefinition(ImportBeanDefRegInstant.class);
        registry.registerBeanDefinition("importBeanDefRegInstant", rootBeanDefinition);

    }
}
