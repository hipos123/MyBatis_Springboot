package com.yaoxj.parallel.countdownlatch;

import java.sql.SQLOutput;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-06-04 09:41
 **/
public class NoCountDownLatchDemo {
    public static void main(String[] args) {
        noCountDownLatchThread noCountDownLatchThread=new noCountDownLatchThread();
        new Thread(noCountDownLatchThread).start();
        System.out.println("线程执行完成了========");
    }
}

class noCountDownLatchThread implements  Runnable{
    int sum=0;
    @Override
    public void run() {
        for (int i=0;i<10;i++){
            sum+=i;
            System.out.println(sum);
        }
    }
}

