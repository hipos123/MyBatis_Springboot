package com.yaoxj.xunhuan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-07-23 17:34
 **/


public class BService {

    @Autowired
    private AService aService;

    public void bTest() {
        System.out.println("this is bibibii test");
    }
}
