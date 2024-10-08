package com.feiyun.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/7 16:18
 * @explain  CompletableFuture和线程池
 *
 *      1.没有出啊内自定义线程池，都用默认线程池ForkJoinPool
 *      2.传入了一个自定义线程池
 *      3.调用thenRun 方法执行第二个任务时，则第二个任务和第一个任务是共用一个线程池
 *      4.调用thenRunAsync执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池
 *      5.有可能处理太快，系统优化切换原则，直接使用main线程处理
 *
 *
 *      thenAccept和thenAcceptAsync ，thenApply和thenApplyAsync等，他们之间的区别也是同理
 *
 */
public class CompletableFutureWithThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        try
        {
            CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("1号任务" + "\t" + Thread.currentThread().getName());
                return "abcd";
            },threadPool)
                    .thenRunAsync(() -> {
                try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("2号任务" + "\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("3号任务" + "\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("4号任务" + "\t" + Thread.currentThread().getName());
            });
            System.out.println(completableFuture.get(2L, TimeUnit.SECONDS));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }



}
