package com.feiyun.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/10 10:44
 * @explain 中断只是一种协作协商机制,java 没有给中断增加任何语法，中断的过程完全需要程序员自己实现、
 *
 *  // public void interrupt（） 仅仅是设置线程的中断状态为true ，发起一个协商而不会立刻停止线程
 *  // public static boolean interrupted()  静态方法，Thread.interrupted()  判断线程是否被中断并清除中断状态
 *                                     这个方法做了两件事：1.返回当前线程的中断状态，测试当前线程是否已被中断
 *                                     2.将当前线程的中断状态清零并重新设置为false ，清除线程的中断状态
 *
 *  //public boolean isInterrupted()  判断当前线程是否被中断（通过检查中断标志位）
 */
public class InterruptDemo {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args)
    {


       // m1_volatile();
        //m2_atomicBoolean();
        m3_interrputed();
    }

    private static void m3_interrputed(){
        Thread t1 = new Thread(() -> {
            while (true)
            {
                //
                if(Thread.currentThread().isInterrupted())
                {
                    System.out.println(Thread.currentThread().getName()+"\t isInterrupted()被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 -----hello interrupt api");
            }
        }, "t1");
        t1.start();

        System.out.println("-----t1的默认中断标志位："+t1.isInterrupted());

        //暂停毫秒
        try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }

        //t2向t1发出协商，将t1的中断标志位设为true希望t1停下来
        new Thread(() -> {
            //interrupt（） 仅仅是设置线程的中断状态为true ，发起一个协商而不会立刻停止线程
            t1.interrupt();
        },"t2").start();
        //t1.interrupt();
    }
    private static void m2_atomicBoolean()
    {
        new Thread(() -> {
            while (true)
            {
                if(atomicBoolean.get())
                {
                    System.out.println(Thread.currentThread().getName()+"\t atomicBoolean被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 -----hello atomicBoolean");
            }
        },"t1").start();

        //暂停毫秒
        try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            atomicBoolean.set(true);
        },"t2").start();
    }

    private static void m1_volatile()
    {
        new Thread(() -> {
            while (true)
            {
                if(isStop)
                {
                    System.out.println(Thread.currentThread().getName()+"\t isStop被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 -----hello volatile");
            }
        },"t1").start();

        //暂停毫秒
        try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            isStop = true;
        },"t2").start();
    }
}
