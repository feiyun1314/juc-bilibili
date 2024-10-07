package com.feiyun.cf;

import java.util.concurrent.*;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/8/26 2:53
 * @explain future+线程池异步多线程任务配合，能显著提高程序的执行效率
 *
 */
public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //3个任务，目前开启多个异步任务线程来处理，请问耗时多少？

        long startTime =System.currentTimeMillis();


        ExecutorService threadPool = Executors.newFixedThreadPool(3);


        FutureTask<String> futureTask1 = new FutureTask<String>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task1 over";
        });


        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<String>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task2 over";
        });


        threadPool.submit(futureTask2);
        futureTask1.get();
        futureTask2.get();


        try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        long endTime =System.currentTimeMillis();

        System.out.println("-----costTime:"+(endTime-startTime)+"毫秒");
        System.out.println(Thread.currentThread().getName()+"\t ----end");


    }

    public static void m1(){
        //3个任务，目前只有一个线程mian来处理，请问耗时多少？

        long startTime =System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        long endTime =System.currentTimeMillis();

        System.out.println("-----costTime:"+(endTime-startTime)+"毫秒");
        System.out.println(Thread.currentThread().getName()+"\t ----end");

    }

}
