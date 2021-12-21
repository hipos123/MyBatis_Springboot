package com.yaoxj.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-11-17 10:41
 **/
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        final Random random = new Random();
        final List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    list.add(random.nextInt());
                }
            });
            thread.start();
            thread.join();
        }
        System.out.println(String.format("耗时：%d", (System.currentTimeMillis()) - startTime));
    }
}
