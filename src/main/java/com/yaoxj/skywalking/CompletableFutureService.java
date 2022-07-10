package com.yaoxj.skywalking;


import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.apache.skywalking.apm.toolkit.trace.TraceCrossThread;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@TraceCrossThread
@Service
@Slf4j
public class CompletableFutureService {
    // 获取当前机器的核数
    public static final int cpuNum = Runtime.getRuntime().availableProcessors();

    private static ExecutorService activeService = new ThreadPoolExecutor(cpuNum, 2*cpuNum,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    public String queryGoodsDetailWithCatalogInfo() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cfDetailMap = CompletableFutureUtil.supplyAsync(() -> {
            // 查询商品详情 forkJoinPool .shutdown();
            try {
                TimeUnit.SECONDS.sleep(3);
                log.info("执行了老三===="+TraceContext.traceId());
                return "老三";
            } catch (Exception e) {
                log.error("商品详情查询失败，信息：{}", e.getMessage());
            }
            return null;
        }, activeService);

        CompletableFuture<String> cfSkuMap = CompletableFutureUtil.supplyAsync(() -> {
            // 查询商品详情 forkJoinPool .shutdown();
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("执行了李四===="+TraceContext.traceId());
                return "李四";
            } catch (Exception e) {
                log.error("商品详情查询失败，信息：{}", e.getMessage());
            }
            return null;
        }, activeService);

        CompletableFuture<String> cfResult = cfDetailMap.thenCombine(cfSkuMap,(x,y)->{
            log.info("TraceContext.traceId()==="+TraceContext.traceId());
             return x+y+"eee";
        });
        String s = cfResult.get();
        log.info("最后结果===="+s);
        return s;
    }

}
