/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/30

*/
package org.mt.concurrent.practice.base;

import java.util.concurrent.locks.LockSupport;

public class LockSupportExample {

    public static Object object = new Object();

    static ChangeObjectThread t1 = new ChangeObjectThread("t1");

    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("start in " + getName());
                LockSupport.park();
                System.out.println("end in " + getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }

    /*
        LockSupport 为每一个线程准备了一个使用许可, 初始保持为不可用, 当调用unpark函数时,状态改变为可用,
        而当调用park函数时,若初始状态不可用,则会一直等待, 若状态可用,则立即返回

        所以每一次park函数的调用,只要有对应的unpark调用,线程都不会进入无线等待,有以下两种情况:
        1. 当unpark函数在park函数执行之前执行,则park函数会直接返回
        2. 当unpark函数在park函数执行之后执行,则park函数在unpark函数执行完之后返回

        这个特点相较于Thread的suspend和resume就要好很多, 如果resume不小心在suspend之前执行了, 则线程可能一直阻塞
     */

}
