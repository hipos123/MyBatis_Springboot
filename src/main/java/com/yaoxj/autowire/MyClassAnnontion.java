package com.yaoxj.autowire;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-06 16:39
 **/
public class MyClassAnnontion {

    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Target(ElementType.TYPE)
    public @interface EnableSpringStudy111{}


}
