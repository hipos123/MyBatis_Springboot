package com.yaoxj.interfaceautowire;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-10-16 17:31
 **/

@Component("zhifubao")
public class ZhiFuBaoHandler implements MyInterface{
    @Override
    public void getName() {
        System.out.println("这边是支付宝的方法");
    }
}
