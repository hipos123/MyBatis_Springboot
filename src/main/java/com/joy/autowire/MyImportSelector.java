package com.joy.autowire;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-07-13 17:24
 **/
public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[0];
    }

    //这个模式就是自动装配的雏形，接着就是开始优化这个模式，将字符串这个内容放到配置文件中，而不是硬编码写在代码里面。
/*
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.joy.ascept.ImportSelectorInstant"};
    }
*/


}
