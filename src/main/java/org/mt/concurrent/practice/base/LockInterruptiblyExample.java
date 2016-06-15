/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/15

*/
package org.mt.concurrent.practice.base;

import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyExample implements Runnable{

    public static ReentrantLock lock1 = new ReentrantLock();

    public static ReentrantLock lock2 = new ReentrantLock();

    private int lock;

    public LockInterruptiblyExample(int lock) {
        this.lock = lock;
    }


    public void run() {
        try{
            if (lock == 1) {
                lock1.lockInterruptibly();
                Thread.sleep(500);
                lock2.lockInterruptibly();
            } else if (lock == 2) {
                lock2.lockInterruptibly();
                Thread.sleep(500);
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getId()+":线程被中断");
        }finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId()+":线程退出");
        }
        System.out.println(Thread.currentThread().getId()+":线程执行结束");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new LockInterruptiblyExample(1));
        Thread t2 = new Thread(new LockInterruptiblyExample(2));
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
    }

    /*
        通过lockInterruptibly,则在等待锁的过程中可以被中断,便于控制中断响应,这个要比synchronize灵活一些
     */
}
