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

import java.util.concurrent.locks.ReentrantLock;

public class TryLockSolveDeadLockExample implements Runnable {

    public static ReentrantLock lock1 = new ReentrantLock();

    public static ReentrantLock lock2 = new ReentrantLock();

    int lock;

    public TryLockSolveDeadLockExample(int lock) {
        this.lock = lock;
    }

    public void run() {
        if (lock == 1) {
            while (true) {
                if (lock1.tryLock()) {
                    try {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock2.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " job is done");
                                return;
                            } finally {
                                //还是那个问题,为何unlock必须放在finally子句中
                                lock2.unlock();
                            }
                        }
                    } finally {
                        lock1.unlock();
                    }
                }
            }
        } else if (lock == 2) {
            while (true) {
                if (lock2.tryLock()) {
                    try {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock1.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " job is done");
                                return;
                            } finally {
                                //还是那个问题,为何unlock必须放在finally子句中
                                lock1.unlock();
                            }
                        }
                    } finally {
                        lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new TryLockSolveDeadLockExample(1));
        Thread t2 = new Thread(new TryLockSolveDeadLockExample(2));
        t1.start();
        t2.start();
    }

    /*
       通过while和trylock的配合,不再是无限等待,可以用于解决常见的死锁问题
       unlock 之所以放在finally子句中,是为了保证不管前面代码抛出任何异常, 都保证锁的释放, 从而避免死锁
     */
}
