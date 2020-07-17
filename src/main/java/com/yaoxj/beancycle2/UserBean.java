package com.yaoxj.beancycle2;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-07-14 17:52
 **/
public class UserBean implements InitializingBean, DisposableBean {
    public UserBean() {
        System.out.println("UserBean构造函数");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("调用实现DisposableBean的destroy方法....");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用实现InitializingBean的afterPropertiesSet方法......");
    }

    public void initUser(){
        System.out.println("执行initMethod方法.....");
    }
    public void destroyUser(){
        System.out.println("执行destroyMethod方法......");
    }

    @PostConstruct
    public void postct(){
        System.out.println("执行PostConstruct方法...");
    }


}
