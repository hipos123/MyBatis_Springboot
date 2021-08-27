package com.yaoxj.beancycle.proxyfactorybean2;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-17 15:49
 **/
@Component
public class ConsolePrint implements IPrint {
    @Override
    public void print(String msg) {
        System.out.println("console print: " + msg);
    }

    @Override
    public boolean verify(Integer condition) {
        return condition <= 0;
    }
}

