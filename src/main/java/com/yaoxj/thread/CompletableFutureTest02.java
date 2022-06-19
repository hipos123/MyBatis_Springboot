package com.yaoxj.thread;

import java.util.concurrent.*;

public class CompletableFutureTest02 {
    public static void main(String[] args) throws ExecutionException {
        /**
         * CompletionStage接口定义了任务编排的方法，执行某一阶段，可以向下执行后续阶段。
         * 异步执行的，默认线程池是ForkJoinPool.commonPool()，但为了业务之间互不影响，且便于定位问题，强烈推荐使用自定义线程池。
         */

        int poolSize = Runtime.getRuntime().availableProcessors();
//        默认初始化开始创建核心线程数量，最大是200个线程，多余的请求将会放到阻塞队列中，空闲的线程将在 10秒之后释放，
//        如果连阻塞队列都放不下，就看配置的策略是什么，拒绝策略的话，直接拒绝掉多余的请求
        //completableFuture.join()  不需要捕获异常，completableFuture.get() 要捕获异常。2个都是用来获取返回值。

        //runAsync 是没有返回值的,2个参数，第一个执行的方法，第二个自定义线程池
        //supplyAsync  这方法有返回值。会阻塞后续的操作。completableFuture.get() 会阻塞

        //whenComplete 这个是异步操作，可以获取到返回值和异常信息，但是如果出现异常，没办法返回一个默认的值，
        //但是exceptionally 就可以在出现异常的情况下，给你一个默认的返回值。

        //handle 方法既可以感知返回值，也可以知道异常信息，对于出现的异常情况可以新增自己的返回值，可以做对于的处理，其实就是whenComplete 和exceptionally的结合体。


        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(poolSize,200,10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),Executors.defaultThreadFactory(),new  ThreadPoolExecutor.AbortPolicy());

        System.out.println("主线程开始执行===="+Thread.currentThread().getName());


     /*   CompletableFuture completableFuture=CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("async  task run,"+Thread.currentThread().getName());
            }
        });*/


     /*   CompletableFuture completableFuture=CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("async  task run,"+Thread.currentThread().getName());
            }
        },threadPoolExecutor);*/

       /*    CompletableFuture completableFuture=CompletableFuture.supplyAsync(() -> {
               System.out.println("async  task run,"+Thread.currentThread().getName());
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               return 10/2;
           },threadPoolExecutor);
        System.out.println("获取异常操作返回结果===="+completableFuture.get());
        System.out.println("我先操作了。再见，你慢慢等结果吧===");*/


        //whenComplete exceptionally
    /*    CompletableFuture completableFuture=CompletableFuture.supplyAsync(() -> {
//            System.out.println("async  task run,"+Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10/0;
        },threadPoolExecutor).whenComplete((result,exception)->{
            if(null!=result){
                System.out.println("执行玩笑的返回结果是"+result);
            }

            if(exception!=null){
                System.out.println("如果出现异常，异常信息是"+exception);

            }
        }).exceptionally((exception) ->{
            return 0;
        } );*/


        CompletableFuture completableFuture=CompletableFuture.supplyAsync(() -> {
//            System.out.println("async  task run,"+Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10/0;
        },threadPoolExecutor).handle((result,exception) ->{
            if(null!=result){
                System.out.println("执行玩笑的返回结果是"+result);
            }
            if(exception!=null){
                System.out.println("如果出现异常，异常信息是"+exception);
                return 0;
            }
            return 0;
        } );

        System.out.println("join 方法返回值=="+completableFuture.join());

//        try {
//            System.out.println("获取异常操作返回结果===="+completableFuture.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("我先操作了。再见，你慢慢等结果吧===");


        System.out.println("主线程执行结束===="+Thread.currentThread().getName());

    }
}
