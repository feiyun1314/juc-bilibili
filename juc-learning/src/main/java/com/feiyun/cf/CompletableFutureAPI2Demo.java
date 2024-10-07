package com.feiyun.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/7 15:53
 * @explain 对计算结果进行处理 thenApply handle
 * thenApply(f)
 * handle((f,e) 带有异常参数
 *
 * 推荐使用thenApply
 */
public class CompletableFutureAPI2Demo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() ->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("111");
            return 1;
        },threadPool).handle((f,e) ->{
            int i=10/0;
            System.out.println("222");
            return f+2;
        }).handle((f,e) ->{
            System.out.println("333");
            return f+3;
        }).whenComplete((v,e) ->{
            if(e == null){
                System.out.println("--计算结果---:"+v);
            }
        }).exceptionally(e ->{
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null ;
        });

        System.out.println(Thread.currentThread().getName()+"--------"+"主线程先去忙其他任务");
        threadPool.shutdown();
    }
}

