package com.yaoxj.interfaceautowire;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-10-16 17:32
 **/
@Component("weixin")
public class WeiXinHandler implements  MyInterface{
    @Override
    public void getName() {
        System.out.println("这边是微信的方法");
    }
}
