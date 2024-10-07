package com.feiyun.cf;

import java.util.concurrent.*;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/7 14:11
 * @explain
 */
public class CompletableFutureBuildDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //无返回值
/*        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<Void> completableFuture =CompletableFuture.runAsync(()->{
        System.out.println(Thread.currentThread().getName());
        //暂停几秒钟
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        },threadPool);
        System.out.println(completableFuture.get());
        threadPool.shutdown();*/

        //有返回值
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<String> completableFuture=CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "hello supplyAsync";
        },threadPool);

        System.out.println(completableFuture.get());
        threadPool.shutdown();
    }
}
