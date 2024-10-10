package com.feiyun.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/10 9:32
 * @explain 公平锁：是指多个线程按照申请锁的顺序来获取锁
 *           非公平锁是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁，在
 *           高并发情况下，有可能造成优先级翻转或者饥饿的状态（某个线程一直得不到锁）
 *            ReentrantLock lock =new ReentrantLock(false); //表示非公平锁，后来的也有可能获取锁
 *            ReentrantLock lock =new ReentrantLock() //默认为非公平锁
 */

class Ticket //资源类，模拟3个售票员卖完50张票
{
    private int number = 50;

    ReentrantLock lock =new ReentrantLock(true);// 非公平锁 +true变成公平锁

    Object lockObject = new Object();

    int i=1;
    public void sale()  {
       /* synchronized (lockObject) {
            if(number > 0)
            {
                System.out.println(Thread.currentThread().getName()+"卖出第：\t"+(number--)+"\t 还剩下:"+number);
            }
        }*/
        lock.lock();
        try {
            if(number > 0){
                number--;
                System.out.println(Thread.currentThread().getName()+"卖出第：\t"+(i++)+"\t 还剩下:"+number);
            }
        }catch (Exception e){
          e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> { for (int i = 0; i <55; i++) ticket.sale(); },"a").start();
        new Thread(() -> { for (int i = 0; i <55; i++)  ticket.sale(); },"b").start();
        new Thread(() -> { for (int i = 0; i <55; i++)  ticket.sale(); },"c").start();
    }

}
