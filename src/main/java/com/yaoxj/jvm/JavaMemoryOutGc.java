package com.yaoxj.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-04-27 14:10
 **/
public class JavaMemoryOutGc {
    public static void main(String[] args) {
        List<JavaMemoryOutGc> list = new ArrayList<>();
        while (true) {
            list.add(new JavaMemoryOutGc());
           /* try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
//    AtomicInteger atomicInteger=new AtomicInteger();
}
