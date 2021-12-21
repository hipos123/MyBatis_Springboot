package com.yaoxj.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-11-17 10:41
 **/
public class ThreadTest2 {
    public static void main(String[] args) throws InterruptedException {
        int poolSize = Runtime.getRuntime().availableProcessors();
        System.out.println(poolSize);
    }
}
