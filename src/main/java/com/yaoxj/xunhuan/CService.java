package com.yaoxj.xunhuan;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-07-23 17:34
 **/


public class CService {

    @Autowired
    private AService aService;

    public void cTest() {
        System.out.println("this is CSERVICE test");
    }
}
