package com.zzw.trta.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFutureTest
 *
 * @author zhaozhiwei
 * @date 2020/3/30 22:24
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("CompletableFuture execute...");
            Thread thread = Thread.currentThread();
            System.out.println("thread name: " + thread.getName());
            System.out.println("thread id: " + thread.getId());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ok";
        });
        Thread.sleep(5000);
//        stringCompletableFuture.get();
    }
}
