package com.feiyun.lockSupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/10 10:40
 * @explain LockSupport 用于创建锁和其他同步类的基本线程阻塞原语
 *
 * 1.如果要使用wait() 和notify()  必须要使用synchronized持有锁  同步代码块或者方法里
 * 2.如果使用 await() 和signal() 必须使用lock.lock()持有锁
 *
 * LockSupport类使用了一种名为Permit（许可）的概念来做到阻塞和唤醒线程的功能，每个线程都有一个许可(permit) 许可的累加上线为1
 *
 *
 * 调用park()方法，permit许可证默认没有不能放行，所以一开始调park()方法当前线程就会阻塞，知道别的线程给当前线程的发放permit，park方法才会被唤醒
 * 调用unPark()方法 就会将thread线程的许可证Permit发放，会主动唤醒Park线程，即之前阻塞中的LockSupport.park()方法会立即返回
 *
 * 重点说明：
 */



public class LockSupportDemo {

    public static void main(String[] args) {

        //syncWaitNotity();

        //lockAwaitSignal();


        Thread t1 = new Thread(() -> {
            //try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t ----come in  "+System.currentTimeMillis());
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ----被唤醒  "+System.currentTimeMillis());
        }, "t1");
        t1.start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName()+"\t ----发出通知");
        },"t2").start();


    }



    private static void lockAwaitSignal() {
        Lock lock =new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread( () ->{
            //暂停几秒钟线程
            //try { TimeUnit.SECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
            lock.lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+"\t ----come in");
                condition.await();
                System.out.println(Thread.currentThread().getName()+"\t ----被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"t1").start();

        //暂停几秒钟线程
        //try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }


        new Thread( ()->{
            lock.lock();
            try
            {
                condition.signal();
                System.out.println(Thread.currentThread().getName()+"\t ----发出通知");
            }finally {
                lock.unlock();
            }
        },"t1").start();
    }

    private static void syncWaitNotity() {
        Object objectLock =new Object(); //同一把锁，类似同意资源

        new Thread(() ->{
            //try { TimeUnit.SECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName()+"\t --------come in");
                try {
                    objectLock.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t -----被唤醒");
                }
            },"t1").start();


        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(() ->{
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName()+"\t ----发出通知");

            }
        },"t2").start();
    }

}
