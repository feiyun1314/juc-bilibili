package com.feiyun.cf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/8/26 3:13
 * @explain 1.get容易导致阻塞，一般建议放在程序后面，一旦调用，非要等到结果才会离开，不管你是否计算完成，容易程序阻塞
 *           2.加入我不愿意等待很长时间，我希望过时不候，可以自动离开  futureTask.get(3,TimeUnit.SECONDS) //等待三秒钟如果没有执行完直接报错
 *           3.isDone() 不断轮询会消耗无谓的cpu资源
 *
 */
public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask=new FutureTask<String>(() ->{
            System.out.println(Thread.currentThread().getName()+"\t ----come in");
            TimeUnit.SECONDS.sleep(5);
            return "task over";
        });
        Thread t1 = new Thread(futureTask, "t1");
        t1.start();


        System.out.println(Thread.currentThread().getName()+"\t ---忙其他任务了");

        //System.out.println(futureTask.get()); //一旦调用get 会造成队列阻塞 一定的那个要等到结果才会离开
        //System.out.println(futureTask.get(3,TimeUnit.SECONDS)); //等待三秒钟如果没有执行完直接报错

        while (true){
            //不断轮询
            if (futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("正在处理中");
            }
        }



    }
}


