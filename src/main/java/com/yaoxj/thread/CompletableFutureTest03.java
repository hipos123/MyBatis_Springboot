package com.yaoxj.thread;

import com.github.pagehelper.StringUtil;

import java.util.concurrent.*;

/**
 * 线程合并操作
 * runAfterBoth： task1和task2完成之后，再开始做当前任务
 * thenAcceptBoth：组合2个future，获取2个future的返回值，然后处理任务，处理自己的这个任务是没有返回值的。
 * thenCombine 组合2个future，获取2个future的返回值，然后处理自己的future，并获取自己的future返回值。
 *
 * thenCombineAsync ,thenAcceptBothAsync,runAfterBothAsync 这三个方法，都可以自己加上自己的线程池，而不用自带的ForkJoinPool
 */
public class CompletableFutureTest03 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * CompletionStage接口定义了任务编排的方法，执行某一阶段，可以向下执行后续阶段。
         * 异步执行的，默认线程池是ForkJoinPool.commonPool()，但为了业务之间互不影响，且便于定位问题，强烈推荐使用自定义线程池。
         */

        int poolSize = Runtime.getRuntime().availableProcessors();


        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(poolSize,200,10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),Executors.defaultThreadFactory(),new  ThreadPoolExecutor.AbortPolicy());

        System.out.println("主线程开始执行===="+Thread.currentThread().getName());

        CompletableFuture completableFuture=CompletableFuture.supplyAsync(() -> {
            System.out.println("task01===="+Thread.currentThread().getName());
            return 10/2;
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

        CompletableFuture completableFuture02=CompletableFuture.supplyAsync(() -> {
            System.out.println("task02===="+Thread.currentThread().getName());
            return "hello";
        },threadPoolExecutor).whenCompleteAsync((result,exception) ->{
            if(StringUtil.isNotEmpty(result)){
                System.out.println("task02 result=="+result);
            }
        });


/*        completableFuture02.runAfterBoth(completableFuture,()->{
            System.out.println("两个执行完成了之后开始做这操作");
        });*/

      /*  completableFuture.thenAcceptBoth(completableFuture02,(t1,t2)->{
            System.out.println("task1 result="+t1+"&&&&task02 result==="+t2);
            System.out.println("在这里做其他事情=====");
        });*/

        CompletableFuture completableFuture03 = completableFuture.thenCombine(completableFuture02, (t1, t2) -> {
            System.out.println("task1 result=" + t1 + "&&&&task02 result===" + t2+"&&Thread name"+Thread.currentThread().getName());
            System.out.println("在这里做其他事情=====");
            return 12;
        });
        System.out.println("task result==="+completableFuture03.get());


//        System.out.println("join 方法返回值=="+completableFuture.join());

//        try {
//            System.out.println("获取异常操作返回结果===="+completableFuture.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("我先操作了。再见，你慢慢等结果吧===");


        System.out.println("主线程执行结束===="+Thread.currentThread().getName());

    }
}
