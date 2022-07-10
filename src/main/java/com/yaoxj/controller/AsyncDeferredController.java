package com.yaoxj.controller;

import com.yaoxj.deferred.LongTimeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncDeferredController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LongTimeTask taskService;

    @Autowired
    public AsyncDeferredController(LongTimeTask taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/deferred")
    public DeferredResult<String> executeSlowTask() {
        System.out.println(Thread.currentThread().getName() + "进入executeSlowTask方法");
        DeferredResult<String> deferredResult = new DeferredResult<>(3000l,"超时了");

        // 调用长时间执行任务
        taskService.execute(deferredResult);
        // 当长时间任务中使用deferred.setResult("world");这个方法时，会从长时间任务中返回，继续controller里面的流程
        System.out.println(Thread.currentThread().getName() + "从executeSlowTask方法返回");
        // 超时的回调方法
        deferredResult.onTimeout(new Runnable(){

            @Override
            public void run() {
                logger.info(Thread.currentThread().getName() + " onTimeout");
                // 返回超时信息
                deferredResult.setErrorResult("time out!");
            }
        });

        // 处理完成的回调方法，无论是超时还是处理成功，都会进入这个回调方法
        deferredResult.onCompletion(new Runnable(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " onCompletion");
            }
        });

        return deferredResult;
    }
}
