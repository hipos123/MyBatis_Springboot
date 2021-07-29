package com.yaoxj.parallel.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-06-04 10:02
 **/
public class CountDownLatchDemo {
    public static void main(String[] args) {
        //countdownlatch 是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕之后，计时器的值就-1，直到计数器的值为0时。
        //表示所有线程执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
        CountDownLatch countDownLatch= new CountDownLatch(5);

        for (int i=0;i<5;i++){
            new Thread(new CountDownLatchThread(countDownLatch)).start();
        }
        try {
            countDownLatch.await();//主线程会在这边等待，直到其他线程都执行完了之后才会继续执行主线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程执行完成了========");
    }
}


class CountDownLatchThread implements Runnable{
    private CountDownLatch countDownLatch=null;
    int sum=0;
    public CountDownLatchThread(CountDownLatch countDownLatch){
        this.countDownLatch=countDownLatch;
    }
    @Override
    public void run() {
        for (int i=0;i<100;i++){
            sum+=i;
            System.out.println(sum);
        }
        countDownLatch.countDown();
    }
}
