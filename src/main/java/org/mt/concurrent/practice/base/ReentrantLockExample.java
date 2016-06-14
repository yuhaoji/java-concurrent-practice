/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/14

*/
package org.mt.concurrent.practice.base;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample implements Runnable {

    public static ReentrantLock reentrantLock = new ReentrantLock();

    public static volatile int i = 0;

    public void run() {
        for (int j = 0 ; j<10000000;j++) {
            //一个线程允许两次获得同一把重入锁,但获取之后也要进行相应次数的解锁
            reentrantLock.lock();
            //reentrantLock.lock();
            try {
                i++;
            }finally {
                //为何必须在finally子句中释放锁?
                reentrantLock.unlock();
                //reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockExample reentrantLockExample = new ReentrantLockExample();
        Thread t1 = new Thread(reentrantLockExample);
        Thread t2 = new Thread(reentrantLockExample);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
