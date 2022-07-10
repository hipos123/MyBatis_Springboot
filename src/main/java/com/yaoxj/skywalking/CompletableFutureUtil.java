package com.yaoxj.skywalking;

import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;
import org.apache.skywalking.apm.toolkit.trace.SupplierWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * @author zhangxiaohui
 * @date 2021/9/6 15:00
 */
public class CompletableFutureUtil {

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(SupplierWrapper.of(supplier));
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(SupplierWrapper.of(supplier), executor);

    }

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(RunnableWrapper.of(runnable));
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
        return CompletableFuture.runAsync(RunnableWrapper.of(runnable), executor);
    }


}