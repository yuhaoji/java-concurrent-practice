/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/3

*/
package org.mt.concurrent.practice.thread;

public class ThreadGroupExample implements Runnable {

    public static void main(String[] args) {
        ThreadGroup tg = new ThreadGroup("PrintGroup");
        Thread t1 = new Thread(tg, new ThreadGroupExample(), "Thread1");
        Thread t2 = new Thread(tg, new ThreadGroupExample(), "Thread2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        tg.list();
    }


    public void run() {
        String groupAndThreadName = Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName();
        System.out.println("I am " + groupAndThreadName);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
        ThreadGroup 比较实用的就是activeCount方法,可以获得一个估算的活跃线程数的大小
     */
}
