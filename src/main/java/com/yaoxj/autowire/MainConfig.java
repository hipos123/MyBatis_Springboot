package com.yaoxj.autowire;

import com.demo.starter.properties.Demo1Properties;
import com.demo.starter.properties.DemoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@ComponentScan("com.yaoxj.autowire")
@Configuration
@EnableConfigurationProperties({MyImportProperties.class})
//@Import(value = {InstantE.class,MyImportSelector.class,MyImportBeanDefinitionRegistry.class})
@EnableSpringStudy
public class MainConfig {


    //import就相当于导入一个bean的注解，加载的是bean定义文件到beandefinitionMap里面，如果你使用这个bean，就需要显示的
    //使用autowire注解，注入bean.在ioc容器里面。如果以import导入的bean，beanname也就是key是完整的类名，使用的时候要注意一下
    //只有使用ImportBeanDefinitionRegistrar的方式用的是自定义的beanname
    @Bean
    public InstantD instantD(){
        return new InstantD();
    }



    //在这边可以定义很多Bean，这边可以定义很多redisconfig的配置信息，如果不在这边定义bean，可以直接使用import进行导入。


}
