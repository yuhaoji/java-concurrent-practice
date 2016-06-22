/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/13

*/
package org.mt.concurrent.practice.collection;

import java.util.ArrayList;

public class ArrayListMultiThreadExample {

    static ArrayList<Integer> list = new ArrayList<Integer>(10);

    private static class AddThread implements Runnable {
        public void run() {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new AddThread());
        Thread thread2 = new Thread(new AddThread());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(list.size());
    }
    /*
        由于多线程访问冲突,会报以下错误
        Exception in thread "Thread-0" Exception in thread "Thread-1" java.lang.ArrayIndexOutOfBoundsException: 23
            at java.util.ArrayList.add(ArrayList.java:441)
            at org.mt.concurrent.practice.collection.ArrayListMultiThreadExample$AddThread.run(ArrayListMultiThreadExample.java:26)
            at java.lang.Thread.run(Thread.java:745)
        java.lang.ArrayIndexOutOfBoundsException: 22
            at java.util.ArrayList.add(ArrayList.java:441)
            at org.mt.concurrent.practice.collection.ArrayListMultiThreadExample$AddThread.run(ArrayListMultiThreadExample.java:26)
            at java.lang.Thread.run(Thread.java:745)
        可以具体分析一下ArrayList的源码,详述不支持多线程的原因
     */

}
