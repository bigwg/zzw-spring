package com.zzw.trta.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaozhiwei
 * @date 2018/10/29 21:46
 */
public class ExecutorTest {

    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public void f2() {
        for (int i = 0; i < 5; i++) {
            System.out.println("i = " + i);
            executor.execute(new com.zzw.trta.thread.SyncTest());
        }
    }

    public class SyncTest implements Runnable {
        /*volatile*/ int a = 100;

        synchronized void f1() throws InterruptedException {
            a = 1000;
            Thread.sleep(5000);
            System.out.println("a = " + a);
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

    public static void main(String[] args) {
//        for (int i = 0; i < 5; i++) {
//            executor.execute(new com.zzw.trta.thread.SyncTest());
//        }
//        executor.shutdown();
        System.out.println(-1 << 29);
    }
}
