package com.yaoxj.autowire;

import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name;
    private int age;

    public Person(String name) {
        System.out.println("调用name的构造函数" + name);
        this.name = name;
    }


    public Person(String name, int age) {
        System.out.println("调用name的构造函数" + name);
        this.name = name;
        this.age = age;
    }

    public Person(int age) {
        System.out.println("调用age的构造函数" + age);
        this.age = age;
    }

    public Person() {
        System.out.println("默认情况下调用无参的构造函数");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
