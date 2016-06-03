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

public class DaemonTreadExample {

    public static class DaemonThread extends Thread{
        @Override
        public void run() {
            while (true) {
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new DaemonThread();
        /*
            如果要设置线程为后台线程,则需要在线程start之前设置,否则会报一下错误
            Exception in thread "main" java.lang.IllegalThreadStateException
	            at java.lang.Thread.setDaemon(Thread.java:1388)
	            at org.mt.concurrent.practice.thread.DaemonTreadExample.main(DaemonTreadExample.java:37)
         */
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);
    }
    /*
        但线程不设置setDaemon(true)时,其为用户线程,一个系统内当所有用户线程都结束,只有守护线程时,jvm将会退出
        因此上面的例子中,在未setDaemon(true)时,即使主线程结束,子线程仍会不断打印
        在设置了线程为守护线程后,主线程结束时,守护子线程也会相应地结束
     */
}
