package com.yaoxj.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-07-13 17:24
 **/
public class MyImportSelector implements ImportSelector {

/*    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[0];
    }*/

    //1,这个模式就是自动装配的雏形，接着就是开始优化这个模式，将字符串这个内容放到配置文件中，而不是硬编码写在代码里面。
/*    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        return new String[]{"com.yaoxj.autowire.ImportSelectorInstant"};
    }*/

    //2,可以将这个写死的常量放到配置文件中

/*    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        ResourceBundle resource = ResourceBundle.getBundle("myimport");
       String key = resource.getString("spring.enableauoconfig.val");
        System.out.println("key======="+key);
        return new String[]{key};
    }*/

    //3.使用这个模式的时候，不要在mainconfig中加上
    //@Import(value = {InstantE.class,MyImportSelector.class,MyImportBeanDefinitionRegistry.class})
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> autoConfigurations = getAutoConfigurations();
        return StringUtils.toStringArray(autoConfigurations);
    }

    protected List<String> getAutoConfigurations() {
//            MyClassAnnontion.EnableSpringStudy111.class
        //org.springframework.boot.autoconfigure.EnableAutoConfiguration
        // EnableAutoConfiguration.class


        List<String> autoConfigurations = SpringFactoriesLoader.loadFactoryNames(
                EnableSpringStudy.class, MyImportSelector.class.getClassLoader());
        return autoConfigurations;
    }

}
