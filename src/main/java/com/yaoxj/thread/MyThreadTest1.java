package com.yaoxj.thread;

import com.yaoxj.util.JsonUtil;

import java.text.MessageFormat;
import java.util.concurrent.*;

/**
 * 初始化线程的4种方法
 * 1，继承Thread
 * 2，实现runnable接口
 * 3，实现callable接口+FutureTask(可以拿到返回值，可以处理异常)
 * 4，线程池
 *
 * 区别：
 *   1，2，3都是直接new一个线程，不能很好的控制资源。  4使用线程池可以和好的控制资源
 *   1，2 没有返回值， 3有返回值。 4 可以有返回值也可以没有返回值execute 和submit
 */
public class MyThreadTest1 {
    public static  ExecutorService executorService = Executors.newFixedThreadPool(5);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("进入主方法开始-----");
//        new Thread01().start();
//        new Thread(new Thread02()).start();
//        FutureTask futureTask=new FutureTask(new CallAble01());
//        new Thread(futureTask).start();
        //阻塞等待整个线程执行完成返回结果倒主线程。
//        Object o = futureTask.get();

        executorService.execute(new Thread02());

        System.out.println("进入主方法结束-------");

        //1,2,3三种方式都不是非常好，都是new thread 创建一个新的线程进行业务操作，如果高并发场景，那么就会一次性产生n个线程，直接
        //把资源给干没了，影响服务器性能。举个例子，比如公司现在来了一个项目，就招一个人，如果公司一次性来了100个项目，那么就得一次性招
        //100个人来，一次性把公司的工位都坐满了（内存全吃了），所以我们应该将所有的多线程异步任务交给线程池来执行
        //后续我们创建一个10个线程的线程池，来了100个任务，那么就分配给这10个线程处理，线程处理来不及，那么就进行等待，等线程释放出来了，再进行处理。

        int poolSize = Runtime.getRuntime().availableProcessors();
//        默认初始化开始创建核心线程数量，最大是200个线程，多余的请求将会放到阻塞队列中，空闲的线程将在 10秒之后释放，
//        如果连阻塞队列都放不下，就看配置的策略是什么，拒绝策略的话，直接拒绝掉多余的请求
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(poolSize,200,10,TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),Executors.defaultThreadFactory(),new  ThreadPoolExecutor.AbortPolicy());

        //一个线程池，核心数是7，max是20 ，quque是50,100个并发进来怎么分配：
        //
        //7个会立即执行，50个会进入队列，再开13个线程进行执行，剩下的30个看使用的策略是什么。如果是拒绝策略的话，直接拒绝掉。
        //如果不想抛弃，还要继续执行的话，使用 ThreadPoolExecutor.CallerRunsPolicy


    }

    public static class Thread01 extends Thread{
        @Override
        public void run() {
            String format = String.format("开始进入线程 %s", Thread01.currentThread().getName());
            String format1 = MessageFormat.format("线程名称{0}", Thread01.currentThread().getName());

            System.out.println(format);
            int i=10/2;
            System.out.println("返回结果===="+i);
        }
    }

    public static class Thread02 implements Runnable{
        @Override
        public void run() {
            String format= MessageFormat.format("线程名称{0}", Thread01.currentThread().getName());
            System.out.println(format);
            int i=10/2;
            System.out.println("返回结果===="+i);
        }
    }

    public  static class CallAble01 implements Callable{
        @Override
        public Object call() throws Exception {
            String format= MessageFormat.format("线程名称{0}", Thread01.currentThread().getName());
            System.out.println(format);
            int i=10/2;
            System.out.println("callable内部的而结果"+i);
            return i;
        }
    }

}
