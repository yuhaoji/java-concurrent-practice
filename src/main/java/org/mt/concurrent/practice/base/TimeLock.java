/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/17

*/
package org.mt.concurrent.practice.base;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TimeLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();

    public void run() {
        try{
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " is sleep");
                Thread.sleep(6000);

            }else{
                System.out.println("get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TimeLock timeLock = new TimeLock();
        Thread t1 = new Thread(timeLock);
        t1.setName("Thread1");
        Thread t2 = new Thread(timeLock);
        t2.setName("Thread2");
        t1.start();
        t2.start();
    }

    /*
       最终结果是有一个线程获取锁失败并输出get lock failed
       等待锁的过程中,线程jstack 状态为
       "Thread1" prio=5 tid=0x00007fa80308b000 nid=0x5103 waiting on condition [0x000070000134f000]
            java.lang.Thread.State: TIMED_WAITING (parking)
	        at sun.misc.Unsafe.park(Native Method)
	        - parking to wait for  <0x00000007d572cbe8> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
	   可以明显看到在等待一个reentrantlock
	   而sleep的线程状态为
	   "Thread2" prio=5 tid=0x00007fa804850800 nid=0x5303 waiting on condition [0x0000700001452000]
            java.lang.Thread.State: TIMED_WAITING (sleeping)
       都是TIMED_WAITING,状态略有区别
     */
}
