/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/7

*/
package org.mt.concurrent.practice.base;

public class SynchronizedExample implements Runnable {

    static SynchronizedExample synchronizedExample = new SynchronizedExample();

    static int i = 0;

    public synchronized void increase() {
        i++;
    }


    /*
        也可写成
        public void increase() {
            synchronized (this) {
                i++;
            }
        }
        原理上都是获取当前实例对象上的锁
     */


    /*
        还可写成
        public static synchronized void increase() {
            i++;
        }
        或是
        public void increase() {
            synchronized (SynchronizedExample.class) {
                i++;
            }
        }
        此时原理是竞争当前类对象上的锁
     */

    public void run() {
        for (int j = 0; j < 10000000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*
            两个线程在创建时使用相同的Runnable接口实例,从而保证两个线程竞争的实例对象上锁是一致的
            当竞争的锁资源为类对象时,就可以通过不同的Runnable接口实例创建线程,因为类对象只有一个
         */
        Thread t1 = new Thread(synchronizedExample);
        Thread t2 = new Thread(synchronizedExample);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
