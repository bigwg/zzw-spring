package com.zzw.trta.lock;

import org.apache.tools.ant.taskdefs.Sleep;

import java.util.concurrent.Semaphore;

/**
 * TODO
 *
 * @author zhaozhiwei
 * @date 2020/3/30 22:42
 */
public class SemaphoreTest {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("放行1个...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            }).start();
        }
        Thread.sleep(20000);
    }

}
