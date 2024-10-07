package com.feiyun.cf;

import java.util.concurrent.*;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/7 14:22
 * @explain  CompletableFuture的优点 1.异步任务结束时，会主动回调某个对象的方法
 *                                   2.主线程设置好回调后，不再关心异步任务的执行，异步任务之间可以顺序执行
 *                                   3.异步任务出错时，会自动回调某个对象的方法
 */
public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture<Integer> completableFuture=CompletableFuture.supplyAsync(()->{
                System.out.println(Thread.currentThread().getName()+"----come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("-----一秒钟后出结果"+result);
                if(result>1){
                   int i=10/0;
                }
                return result;
            },threadPool).whenComplete((v,e)->{
                if(e==null){
                    System.out.println("-------计算完成，更新系统updateValue:"+v);
                }
            }).exceptionally(e->{
                e.printStackTrace();
                System.out.println("异常情况："+e.getCause()+"异常信息："+e.getMessage());
                return null;
            });
            System.out.println(Thread.currentThread().getName()+"线程先去忙其他任务");


            //主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:暂停3秒钟线程
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

}

    private static void future1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture=CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"----come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("-----一秒钟后出结果"+result);
            return result;
        });

        System.out.println(Thread.currentThread().getName()+"线程先去忙其他任务");
        System.out.println(completableFuture.get());
    }
    }

