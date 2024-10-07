package com.feiyun.cf;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/8/26 2:36
 * @explain  FutureTask 带有返回值的异步多线程
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread2()); //异步多线程

        Thread t1=new Thread(futureTask,"t1");
        t1.start();
        System.out.println(futureTask.get()); //异步线程处理结果
    }

}
/*    class MyThread implements Runnable{

        @Override
        public void run() {

        }
    }*/

     class MyThread2 implements Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.println("come in call()");
            return "hello";
        }
    }

