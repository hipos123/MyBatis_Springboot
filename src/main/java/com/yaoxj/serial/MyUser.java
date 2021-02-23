package com.yaoxj.serial;

import java.io.Serializable;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-02-19 14:40
 **/
public class MyUser  {
    private int age;
    private String name;
    private String sex;
    public MyUser(int age,String name,String sex){
        this.age=age;
        this.name=name;
        this.sex=sex;
    }
}
