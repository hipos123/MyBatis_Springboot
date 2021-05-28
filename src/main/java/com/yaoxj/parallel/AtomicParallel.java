package com.yaoxj.parallel;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-05-08 10:54
 **/
public class AtomicParallel {
    private int num=0;
    AtomicInteger atomicInteger=new AtomicInteger();
    LongAdder longAdder=new LongAdder();
    
    //使用同步锁机制，保证一次只有一个线程拿到锁进行操作
/*    public synchronized  void increase(){

        num++;
    }
    public  long getNum(){
        return num;
    }*/

    //atomicInteger 轻量级锁 使用的是cas机制，比较并交换，底层使用的也是汇编语言的锁保证原子性

/*    public   void increase(){
        atomicInteger.incrementAndGet();
    }

    public  long getNum(){
        return atomicInteger.get();
    }*/

    //LongAdder 轻量级锁 使用的是分段cas机制，效率更高一点，不要每次一但有线程竞争的时候，就升级成重量级锁
    public   void increase(){
        longAdder.increment();
    }

    public  long getNum(){
        return longAdder.longValue();
    }

}
