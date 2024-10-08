package com.feiyun.locks;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/10/8 8:53
 * @explain
 */

class Book extends Object{
    //
}
public class LockSyncDemo {


    Object object= new Object();
    Book book=new Book();
    public void m1()
    {
        synchronized (object)
        {
            System.out.println("----hello synchronized code block");
            //throw new RuntimeException("-----exp");
        }
    }
    public synchronized void m2() {
        System.out.println("----hello synchronized m2");

        }

    public static synchronized void m3() {
        System.out.println("----hello synchronized m3");

    }


    public static void main(String[] args) {

    }
}
