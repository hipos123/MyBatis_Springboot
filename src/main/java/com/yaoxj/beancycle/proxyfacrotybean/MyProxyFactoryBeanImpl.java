package com.yaoxj.beancycle.proxyfacrotybean;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-17 15:12
 **/
public class MyProxyFactoryBeanImpl implements ProxyFactoryBeanInferface{
    @Override
    public void testMethod() {
        System.out.println("这个是一个代理的factoreyBean");
    }
}
