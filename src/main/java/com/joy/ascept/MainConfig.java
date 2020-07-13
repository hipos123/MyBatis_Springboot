package com.joy.ascept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan("com.joy.ascept")
@Configuration
@Import(value = {InstantE.class,MyImportSelector.class,MyImportBeanDefinitionRegistry.class})
public class MainConfig {

    @Bean
    public InstantD instantD(){
        return new InstantD();
    }

}
