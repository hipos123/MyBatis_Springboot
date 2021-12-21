package com.yaoxj.webflux;

import sun.awt.windows.ThemeReader;

import java.util.Arrays;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-11-01 15:28
 **/
public class LamdaDemo {
    static int[] arr = {1, 3, 4, 5, 6, 7, 8, 9, 10};

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("normal 创建哪一个线程");
            }
        }).start();

        new Thread(() -> System.out.println("lamda 创建一个线程")).start();

/*        4.不变模式
        如果读者熟悉多线程程序设计，那么一定对不变模式有所有了解。所谓不变，是指对象在创建后，就不再发生变化。
        比如，java.lang.String就是不变模式的典型。如果你在Java中创建了一个String实例，无论如何，你都不可能改变整个String的值。比如，
        当你使用String.replace()函数试图进行字符串替换时，实际上，原有的字符串对象并不会发生变化，函数本身会返回一个新的String对象，
        作为给定字符替换后的返回值。不变的对象在函数式编程中被大量使用
                */

/*        Arrays.stream(arr).map((x)->x=x+1).forEach(System.out::println);
        System.out.println();
        Arrays.stream(arr).forEach(System.out::println);*/

        for (int i = 0; i < arr.length; i++) {
            arr[i] += 1;
        }
        Arrays.stream(arr).forEach(System.out::println);


/*        5.易于并行
        由于对象都处于不变的状态，因此函数式编程更加易于并行。实际上，你甚至完全不用担心线程安全的问题。我们之所以要关注线程安全，
        一个很大的原因是当多个线程对同一个对象进行写操作时，容易将这个对象“写坏”，更专业的说法是“使得对象状态不一致”。
        但是，由于不变模式的存在，对象自创建以来，就不可能发生改变，因此，在多线程环境下，也就没有必要进行任何同步操作。
        这样不仅有利于并行化，同时，在并行化后，由于没有同步和锁机制，其性能也会比较好。读者可以关注一下java.lang.String对象。
        很显然，String对象可以在多线程中很好的工作，但是，它的每一个方法都没有进行同步处理。*/
    }
}
