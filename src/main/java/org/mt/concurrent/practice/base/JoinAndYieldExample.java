/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/1

*/
package org.mt.concurrent.practice.base;

public class JoinAndYieldExample {

    public volatile static int i = 0;

    public static class AddThread extends Thread {
        @Override
        public void run() {
            for (i = 0; i < 10000000; i++) {

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread addThread = new AddThread();
        addThread.start();
        addThread.join();
//        addThread.yield(); 大部分情况下没啥用,会让出cpu,但是同时也会重新加入cpu的竞争
        System.out.println(i);
    }

    /*
        使用join后的结果: 10000000
        不适用join的结果: 384711
     */

    /**
     * join在jdk内部实现时,实际上是调用的线程对象上的wait方法,
     * 如上面的例子中可以认为是主线程中调用了addThread线程对象的wait方法
     *
     * while (isAlive()) {
     *     wait(0);
     * }
     *
     * 总结一下就是执行线程wait在被等待线程对象上, 因此源码中注解建议我们在实际使用wait方法时最好不好对对线程对象进行wait,
     * 可能会造成一些和系统api的冲突
     *
     * 但是addThread对象在执行完之后是怎么发送的notifyAll消息呢?
     *
     * 网上的结论是线程结束时会隐式地调用当前线程对象上的notifyAll方法
     *
     */

}
