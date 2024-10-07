package com.feiyun.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/7 16:09
 * @explain 对计算结果进行消费
 *        thenRun  任务A执行完执行B,并且B不需要A的结果
 *        thenAccept 任务A执行完执行B,B需要A的结果,但是任务B无返回值
 *        thenApply  任务A执行完执行B,B需要A的结果,同时任务B返回值
 */
public class CompletableFutureAPI3Demo {
    public static void main(String[] args) {
/*        CompletableFuture.supplyAsync(() ->{
            return 1;
        }).thenApply(f ->{
            return f+2;
        }).thenApply(f ->{
            return f+3;
        }).thenAccept(System.out::println);*/

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() ->{}).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(r -> System.out.println(r)).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(r -> r+"resultB").join());


    }
}
