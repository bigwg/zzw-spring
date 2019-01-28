package com.zzw.trta.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaozhiwei
 * @description 基于zooKeeper的curator分布式锁测试类
 * @date 2018/10/1 11:30
 */
@Slf4j
public class DistributeLockTest {

    private static final String ADDRESS = "192.168.130.11:2181";

    public static void main(String[] args) throws InterruptedException {
        //1、重试策略：初试时间为1s 重试3次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 1);
        //2、通过工厂创建连接
        CuratorFramework client = CuratorFrameworkFactory.newClient(ADDRESS, 6000, 6000, retryPolicy);
        //3、开启连接
        client.start();
//        Thread.sleep(5000);
        //4 分布式锁
        final InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");
        //读写锁
        //InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/readwriter");
        ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(5, 10, 600, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ZooTestThreadFactory());
        for (int i = 0; i < 5; i++) {
            fixedThreadPool.execute(() -> {
                boolean flag = false;
                try {
                    //尝试获取锁，最多等待5秒
                    Thread currentThread = Thread.currentThread();
//                    flag = mutex.acquire(5, TimeUnit.SECONDS);
//                    if (flag) {
//                        System.out.println("线程 " + currentThread.getName() + " 获取锁成功");
//                    } else {
//                        System.out.println("线程 " + currentThread.getName() + " 获取锁失败");
//                    }
                    mutex.acquire();
                    System.out.println("线程 " + currentThread.getName() + " 获取锁成功");
                    //模拟业务逻辑，延时4秒
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    if (flag) {
//                        try {
//                            mutex.release();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                    try {
                        mutex.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        while (true){
            if (fixedThreadPool.getActiveCount() == 0){
                break;
            }
            Thread.sleep(1000);
        }
        client.close();
        fixedThreadPool.shutdown();
    }

    public static class ZooTestThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("ZooKeeper-Test-Thread-" + thread.getId());
            return thread;
        }
    }
}
