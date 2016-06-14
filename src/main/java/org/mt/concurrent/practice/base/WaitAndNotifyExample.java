/*

======================
Authors:haoji.yu

======================
Description:

wait 和 notify的例子
======================
Major changs:

add by haoji.yu 16/5/31

*/
package org.mt.concurrent.practice.base;

public class WaitAndNotifyExample {

    private final static Object object = new Object();

    public static class T1 extends Thread{
        public void run () {
            synchronized (object) {
                System.out.println(System.currentTimeMillis()+": T1 start! ");
                try{
                    System.out.println(System.currentTimeMillis()+": T1 wait for object! ");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+": T1 end! ");
            }
        }
    }

    public static class T2 extends Thread{
        public void run () {
            synchronized (object){
                System.out.println(System.currentTimeMillis()+": T2 start! notify one thread ");
                object.notify();
                System.out.println(System.currentTimeMillis()+": T2 end! ");
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }

    /*
        结果:
        1464652914067: T1 start!
        1464652914067: T1 wait for object!  //T1在wait的同时会释放掉等待对象上的锁
        1464652914067: T2 start! notify one thread
        1464652914067: T2 end!
        1464652916071: T1 end!  // 在最终被notify唤醒时仍需等待原有等待对象上的锁

        主要结论:
        Object.wait() 和 Thread.sleep() 都可以使线程等待若干时间, 除了wait可以被唤醒外, 另一个主要区别是wait方法会
        释放目标对象的锁,而sleep方法不会释放任何资源

        线程的几种状态:
        WAITING 是在线程执行了wait方法后所处的状态, 同理, 执行join方法也会使线程处于这种状态
        BLOCKED 是线程在竞争锁进入同步代码块时的状态,包括等待锁进入同步代码块, 以及线程在wait之后被notify唤醒之后等待锁的阶段
        RUNNABLE 是jvm中运行的线程状态,但是可能由于竞争cpu资源而暂时闲置
     */

}
