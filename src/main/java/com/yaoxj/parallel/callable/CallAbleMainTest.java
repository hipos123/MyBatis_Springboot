package com.yaoxj.parallel.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-06-04 16:28
 **/
public class CallAbleMainTest {
    public static void main(String[] args) {
        CallableDemo callableDemo=new CallableDemo();
        FutureTask futureTask=new FutureTask(callableDemo);
        new Thread(futureTask).start();
        try {
            Object o = futureTask.get();
            System.out.println("最后的结果==="+o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
