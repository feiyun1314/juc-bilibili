package com.feiyun.interrupt;

import java.util.concurrent.locks.LockSupport;

/**
 * @auther zzyy
 * @create 2022-01-20 11:58
 *
 * public static boolean interrupted()  静态方法，Thread.interrupted()  判断线程是否被中断并清除中断状态
 *  *                                     这个方法做了两件事：1.返回当前线程的中断状态，测试当前线程是否已被中断
 *  *                                     2.将当前线程的中断状态清零并重新设置为false ，清除线程的中断状态
 *
 *
 *   中断标识被清空，如果该方法被连续调用两次，第二次将返回false
 *   除非当前线程在第一次和第二次调用该方法之间再次被interrupt
 *
 *   Thread.interrupted()与 Thread.currentThread().isInterrupted()的区别
 *
 *   方法的注释清晰的表达了”中断状态将会根据传入的ClearInterrupted参数值是否重置“
 *   Thread.interrupted() 静态方法传入的值为true;
 *   Thread.currentThread().isInterrupted() 实例方法传入的值为false
 *
 */
public class InterruptDemo4
{
    public static void main(String[] args)
    {
        //测试当前线程是否被中断（检查中断标志），返回一个boolean并清除中断状态，
        // 第二次再调用时中断状态已经被清除，将返回一个false。


        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());
        System.out.println("----1");
        Thread.currentThread().interrupt();// 中断标志位设置为true
        System.out.println("----2");
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());
        //将当前线程的中断状态清零并重新设置为false ，清除线程的中断状态
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());

        LockSupport.park();

        Thread.interrupted();//静态方法

        Thread.currentThread().isInterrupted();//实例方法
    }
}
