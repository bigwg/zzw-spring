package com.zzw.trta.thread;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/6 9:36
 */
public class SyncTest implements Runnable {
    /*volatile*/ int a = 100;

    synchronized void f1() throws InterruptedException {
        a = 1000;
        Thread.sleep(1);
        System.out.println("a = " + a);
    }

    synchronized void f2() {
        a = 2000;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            SyncTest t = new SyncTest();
            Thread tt = new Thread(t);
            tt.start();
            Thread.sleep(1);
            t.f2();
//            Thread.sleep(1);
            System.out.println("main a = " + t.a);
            Thread.sleep(500);
            System.out.println("---------------------------");
//            Thread.sleep(500);
        }
    }

    @Override
    public void run() {
        try {
            f1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
