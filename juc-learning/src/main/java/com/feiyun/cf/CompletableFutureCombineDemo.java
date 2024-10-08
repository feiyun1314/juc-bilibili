package com.feiyun.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/7 16:40
 * @explain 对计算结果进行合并
 *      连哥哥CompletionStage 任务都完成后，最终能把两个任务的结果以前交给thenCombine来处理，
 *      先完成的先等着，等待其他分支任务
 */
public class CompletableFutureCombineDemo {

    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        });

        CompletableFuture<Integer> result = completableFuture1.thenCombine(completableFuture2, (x, y) -> {
            System.out.println("------开始两个结果合并");
            return x + y;
        });

        System.out.println(result.join());

    }
}
