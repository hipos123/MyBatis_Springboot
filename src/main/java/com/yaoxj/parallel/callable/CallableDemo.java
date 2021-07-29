package com.yaoxj.parallel.callable;

import java.util.concurrent.Callable;

/**
 * @description:
 * 创建线程的方式原来是 new Thread
 * 实现Runnable类。但是实现Runnable，是没有返回值的。
 *
 * 实现Callable，是有返回值的，基本上要配合Future使用。
 *
 * @author: yaoxj
 * @create: 2021-06-04 16:20
 **/
public class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum=0;
        for (int i=0;i<100;i++){

            sum+=i;
            System.out.println(sum);
        }
        return sum;
    }
}
