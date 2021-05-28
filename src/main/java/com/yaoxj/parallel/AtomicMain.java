package com.yaoxj.parallel;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-05-08 10:56
 **/
public class AtomicMain {
    public static void main(String[] args) throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        AtomicParallel atomicParallel=new AtomicParallel();

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<1000000;i++){
                    atomicParallel.increase();
                }
            }
        });
        t1.start();
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<1000000;i++){
                    atomicParallel.increase();
                }
            }
        });
        t2.start();
        for(int i=0;i<1000000;i++){
            atomicParallel.increase();
        }
        for(int i=0;i<1000000;i++){
            atomicParallel.increase();
        }
        for(int i=0;i<1000000;i++){
            atomicParallel.increase();
        }
        t1.join();
        t2.join();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));;
        System.out.println(atomicParallel.getNum());
    }
}
