package com.feiyun.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/10 10:00
 * @explain  可重入锁又名递归锁
 *             是指在同一个线程在外层方法获取锁的时候，再进入该线程的内层方法会自动获取锁（前提，锁对象是同一个对象），不回因为之前已经获取过还没有释放而阻塞
 *
 *          synchronized
 *           ReentrantLock
 */
public class ReEntryLockDemo {
    public  synchronized void m1(){
        //指的是可重复可递归调用的锁，在外层使用锁之后，在内层仍然可以使用，并且不发生死锁，这样的锁就叫做可重入锁。
        System.out.println(Thread.currentThread().getName()+"----come in");
        m2();
        System.out.println(Thread.currentThread().getName()+"\t ----end m1");

    }
    public  synchronized void m2(){
        System.out.println(Thread.currentThread().getName()+"----come in");
        m3();
    }
    public  synchronized void m3(){
        System.out.println(Thread.currentThread().getName()+"----come in ");

    }

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
/*        ReEntryLockDemo reEntryLockDemo =new ReEntryLockDemo();
        new Thread(() ->{
            reEntryLockDemo.m1();
        },"t1").start();*/

        new Thread(() -> {
            lock.lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+"\t ----come in外层调用");
                lock.lock();
                try
                {
                    System.out.println(Thread.currentThread().getName()+"\t ----come in内层调用");
                }finally {
                    lock.unlock();
                }

            }finally {
                // 由于加锁次数和释放次数不一样，第二个线程始终无法获取到锁，导致一直在等待。
                lock.unlock();// 正常情况，加锁几次就要解锁几次

            }
        },"t1").start();

        new Thread(() -> {
            lock.lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+"\t ----come in外层调用");
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }

    private static void reEntryM1() {
        final Object object =new Object();
        new Thread(() ->{
            synchronized (object){
                System.out.println(Thread.currentThread().getName()+"----外层调用");
            }synchronized (object){
                System.out.println(Thread.currentThread().getName()+"----中层调用");
            }synchronized (object){
                System.out.println(Thread.currentThread().getName()+"----内层调用");
            }
        },"t1").start();
    }
}
