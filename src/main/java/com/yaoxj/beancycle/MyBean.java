package com.yaoxj.beancycle;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-06-02 15:55
 **/
public class MyBean {
    private String name;
    public void MyBean(String name){
        this.name=name;
    }

    public  String getMyName(){
        return name+" is vert";
    }
}
