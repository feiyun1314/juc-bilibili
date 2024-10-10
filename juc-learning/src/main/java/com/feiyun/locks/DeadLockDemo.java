package com.feiyun.locks;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/10 10:22
 * @explain死锁产生 的原因：1.系统资源不足  2.进程运行推进的顺序不合适   3.资源分配不当
 *
 *                  排查死锁的原因： 1.1. jps -l 命令查看线程
 *                                 1.2.jstack 进程号
 *
 *                                 2.win+R  图形化查看
 *
 *
 *
 *
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        final Object objectA =new Object();
        final Object objectB =new Object();
        new Thread(() ->{
            synchronized (objectA){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有A锁，希望获得B锁");
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (objectB){
                    System.out.println(Thread.currentThread().getName()+"\t 成功获得B锁");
                }
            }
        },"t1").start();

        new Thread(() ->{
            synchronized (objectB){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有B锁，希望获得A锁");
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (objectA){
                    System.out.println(Thread.currentThread().getName()+"\t 成功获得A锁");
                }
            }
        },"t2").start();
    }
}
