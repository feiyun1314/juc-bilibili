package com.feiyun.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author feiyun
 * @date 2025/1/10 20:35
 * @explain  volatile变量的符合操作不具有原子性
 *              i++ 会出现
 */
class MyNumber{
    int number;
    public synchronized void addPlusPlus(){
        number++;
    }
}
public class VolatileNoAtomicDemo {


    public static void main(String[] args) {
        MyNumber myNumber=new MyNumber();
        for (int i = 0; i < 10; i++) {
            new Thread(() ->{
                for (int j = 0; j < 1000; j++) {
                    myNumber.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }
        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(myNumber.number);
    }
}
