/* Project of UGC team

======================
Authors:haoji.yu

======================
Description:

======================
Major changs:

add by haoji.yu 16/6/24

*/
package org.mt.concurrent.practice.base;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    public static class Soldier implements Runnable {

        private String soldier;

        private final CyclicBarrier cyclicBarrier;

        public Soldier(String soldier, CyclicBarrier cyclicBarrier) {
            this.soldier = soldier;
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run() {
            try {
                //等待士兵到齐的操作
                cyclicBarrier.await();
                doWork();
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt()) % 10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier + " : 任务完成");
        }
    }

    public static class BarrierRun implements Runnable {
        private boolean flag;

        public void run() {
            if (flag) {

                System.out.println("司令: 士兵任务完成!" + Thread.currentThread().getName());
            } else {
                System.out.println("司令: 士兵集结完毕!" + Thread.currentThread().getName());
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new BarrierRun());
        System.out.println("集合队伍!");
        for (int i = 0; i < 10; i++) {
            String soldierName = "士兵" + i;
            System.out.println(soldierName + "报道!");
            Thread thread = new Thread(new Soldier(soldierName, cyclicBarrier));
            thread.start();
        }
    }

    /*
        CyclicBarrier比CountDownLatch更加强大,他可以形成类似于跨栏比赛中的一道道栅栏,但是所有跑道上的线程都必须在栅栏前集合
        才能进行后续的操作,而且每次计数满足之后都可以出发回调
        经过测试发现回调是在最后一个完成的线程中发生的,而不是设置栅栏的线程
        集合队伍!
        士兵0报道!
        士兵1报道!
        士兵2报道!
        士兵3报道!
        士兵4报道!
        士兵5报道!
        士兵6报道!
        士兵7报道!
        士兵8报道!
        士兵9报道!
        司令: 士兵集结完毕!Thread-9
        士兵9 : 任务完成
        士兵0 : 任务完成
        士兵7 : 任务完成
        士兵8 : 任务完成
        士兵1 : 任务完成
        士兵5 : 任务完成
        士兵3 : 任务完成
        士兵2 : 任务完成
        士兵4 : 任务完成
        士兵6 : 任务完成
        司令: 士兵任务完成!Thread-6
        如上,两次回调分别在线程9和线程6中进行,也就是当前的最后完成计数的线程中
     */
}
