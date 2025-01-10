package com.feiyun.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author feiyun
 * @date 2025/1/10 20:18
 *           保证不同线程对某个变量完成操作后结果及时可见，即该共享变量一旦改变所有线程立即可见
 * @explain 不加volatile，没有可见性，程序无法停止
 *          加了volatile，保证可见性，程序可以停止
 */
public class VolatileSeeDemo {

    //static  boolean flag =true;
    static volatile boolean flag =true;


    /**
     * t1	-------come in
     * main	 修改完成
     * t1	-------flag被设置为false，程序停止
     */
    public static void main(String[] args) {

        new Thread(() ->{
            System.out.println(Thread.currentThread().getName() + "\t-------come in");
            while (flag){

            }
            System.out.println(Thread.currentThread().getName() + "\t-------flag被设置为false，程序停止");
        },"t1").start();


        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //更新flag值
        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成 \t"+false);

    }
}
