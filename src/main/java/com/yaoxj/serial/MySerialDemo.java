package com.yaoxj.serial;

import java.io.*;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-02-19 14:39
 **/
public class MySerialDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File("user.ser");

        //查看源码：直接输出字符串，数组，枚举是不会存在问题的
        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file));
    /*    oout.writeObject("111aaa");
        oout.close();*/
        //如果MyUser 没有实现序列化，会报错：NotSerializableException
        MyUser user = new MyUser(18, "test", "nan");
        oout.writeObject(user);
        oout.close();

        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
        Object newUser = oin.readObject();
        oin.close();
        System.out.println(newUser);

    }
}
