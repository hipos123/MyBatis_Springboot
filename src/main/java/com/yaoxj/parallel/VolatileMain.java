package com.yaoxj.parallel;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-05-11 10:49
 **/
public class VolatileMain {
    private static volatile boolean  isContinue=false;
//    private static  boolean  isContinue=false;
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!isContinue){
//                    System.out.println("while do");
                }
                System.out.println("t1 success----");
            }
        });
        t1.start();
        Thread.sleep(2000);

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2 线程准备数据----");
                isContinue=true;
                System.out.println("t2 线程准备数据成功----");
            }
        });
        t2.start();

        Thread t3=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t3 线程isContinue----"+isContinue);
            }
        });
        t3.start();

    }
}
