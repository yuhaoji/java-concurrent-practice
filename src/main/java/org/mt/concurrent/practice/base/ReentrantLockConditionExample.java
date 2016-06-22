/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/20

*/
package org.mt.concurrent.practice.base;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockConditionExample implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();

    public static Condition condition = lock.newCondition();

    public void run() {
        try {
            lock.lock();
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ReentrantLockConditionExample());
        t1.start();
        Thread.sleep(2000);
        lock.lock();
        condition.signal();
//        lock.unlock();
    }

    /*
       await的用法类似于object.wait 在 synchronized 代码块中的使用方式, 在其他线程调用signal方法之后还需要重新获取
       释放掉的锁才能继续执行,上例中如果把最后main函数中的lock.unlock方法注释掉, 则t1线程将永远无法执行结束
       会显示如下栈信息, 一直在等待conditionObject
       await方法内部实现上使用了lockSupport,有别于object.wait 和 object.notify
       "Thread-0" prio=5 tid=0x00007fb473022000 nid=0x5103 waiting on condition [0x000070000134f000]
           java.lang.Thread.State: WAITING (parking)
            at sun.misc.Unsafe.park(Native Method)
            - parking to wait for  <0x00000007d572daa0> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
            at java.util.concurrent.locks.LockSupport.park(LockSupport.java:186)
            at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2043)
            at org.mt.concurrent.practice.base.ReentrantLockConditionExample.run(ReentrantLockConditionExample.java:29)
            at java.lang.Thread.run(Thread.java:745)
     */
}
