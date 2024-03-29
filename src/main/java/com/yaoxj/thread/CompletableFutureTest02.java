package com.yaoxj.thread;

import com.github.pagehelper.StringUtil;

import java.util.concurrent.*;

/**
 * 线程合并操作：2个future只要有一个处理完成，就可以开始做第三个future
 * runAfterEither： 只要有一个future完成，就可以执行第三个任务了，都不需要获取返回值
 *
 * acceptEither：谁先处理完，就获取到谁的返回值，然后开始执行第三个任务，但是第三个任务是没有返回值的。
 *
 * applyToEither:两个future 有一个执行完成，就可以执行第三个任务了，并且可以获取2个future中最先完成的结果，完成第三个任务的时候也有返回值。
 * 同样加了async的话，就是可以使用自己的线程池
 *
 */
public class CompletableFutureTest02 {
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
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10/2;
        },threadPoolExecutor).handle((result,exception) ->{
            if(null!=result){
                System.out.println("future1执行的返回结果是"+result);
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


//        completableFuture.runAfterEither(completableFuture02,()->{
//            System.out.println("third future start");
//        });

//        completableFuture.acceptEither(completableFuture02,(r) ->{
//            System.out.println("获取返回值"+r+",但是没有结果返回");
//        });

        CompletableFuture completableFuture03 = completableFuture.applyToEither(completableFuture02, (r) -> {
            System.out.println("获取到的返回值是" + r);
            return r + " baby";
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
