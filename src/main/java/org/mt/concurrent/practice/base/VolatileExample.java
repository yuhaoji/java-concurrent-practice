/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/2

*/
package org.mt.concurrent.practice.base;

public class VolatileExample {

    private static volatile boolean ready;

    private static volatile int number;


    private static class ReadThread extends Thread{
        @Override
        public void run() {
            while (!ready) {
                System.out.println(number);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadThread().start();
        Thread.sleep(1000);
        number = 40;
        ready = true;
        Thread.sleep(10000);
    }

    /*
        如果ready和number两个字段不加上volatile的属性,则主线程中对number和ready的变更在另一个线程中都将是不可见的
        此时子线程将因为ready字段未改变, 而一直运行下去
     */

}
