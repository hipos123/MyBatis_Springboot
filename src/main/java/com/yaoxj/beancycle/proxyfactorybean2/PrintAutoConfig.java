package com.yaoxj.beancycle.proxyfactorybean2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-17 16:06
 **/
//@Configuration
public class PrintAutoConfig {

    @Bean
    public SpiFactoryBean printSpiPoxy(ApplicationContext applicationContext) {
        return new SpiFactoryBean(applicationContext, IPrint.class);
    }

    @Bean
    @Primary
    public IPrint printProxy(SpiFactoryBean spiFactoryBean) throws Exception {
        return (IPrint) spiFactoryBean.getObject();
    }
}

