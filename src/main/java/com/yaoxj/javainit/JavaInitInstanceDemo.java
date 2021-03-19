package com.yaoxj.javainit;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-03-02 17:14
 **/
public class JavaInitInstanceDemo {

    private static String name="uu";
    private  String name2;
    static{
        System.out.println("static块");
    }
    public JavaInitInstanceDemo(String _name){
        System.out.println("构造函数");
        this.name2=_name;
    }
    public static void main(String[] args) {
        System.out.println("main函数");
        JavaInitInstanceDemo javaInitInstanceDemo=new JavaInitInstanceDemo("utee");

    }
}
