package com.feiyun.base;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author feiyun
 * @date 2024/8/26 2:17
 * @explain 用户线程与守护线程的区别
 *
 * 1.程序退出时的行为：用户线程是程序的主要工作线程，当所有用户线程结束运行后，虚拟机（JVM）才会停止运行。即使还有一些守护线程在运行，
 * JVM也会终止。相反，守护线程的存在并不会阻止JVM的关闭。当所有的用户线程执行完毕后，守护线程会被强制终止，不管它们是否执行完毕。
 * 2.优先级：通常情况下，守护线程的优先级较低。在资源紧张时，系统会优先调度用户线程。
 * 3.设计目的：用户线程是程序的主要工作线程，执行程序的主要任务。而守护线程则是为了支持用户线程的运行而存在的，
 * 通常用于执行一些后台任务，如垃圾回收、自动保存、日志记录等。
 * 4.资源访问：守护线程不能访问普通线程的资源。当一个守护线程想要访问一个普通线程的资源时，如果该普通线程已经结束，
 * 守护线程将无法获取到该资源。
 *
 */
public class DaemonDemo {



   // t1	 开始运行，守护线程
   // main	 ------end 主线程
    public static void main(String[] args) {
        Thread t1=new Thread(() ->{
            System.out.println(Thread.currentThread().getName()+"\t 开始运行，"
                    +(Thread.currentThread().isDaemon()?"守护线程":"用户线程"));
            while (true){

            }
        },"t1");
        t1.setDaemon(true); //必须在start方法之前才生效
        t1.start();

        try {
            //暂停几秒
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"\t ------end 主线程");
    }
}
