package com.yaoxj.xunhuan;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-07-26 16:15
 **/
@Component
@Aspect
public class YlltAspect {

    @Before("execution(public String com.yaoxj.xunhuan.AService.aTest())")
    public  void apsectBefore(){
        System.out.println("aop aspect");
    }
}
