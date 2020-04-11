package com.zzw.trta.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 *
 * @author zhaozhiwei
 * @date 2020/2/29 17:47
 */
public class ReentrantReadWriteLockTest {

    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
//        firstReadSecondWrite(readLock, writeLock);
        firstWriteSecondRead(readLock, writeLock);
        firsReadSecondRead(readLock, writeLock);
    }

    public static void firstReadSecondWrite(ReentrantReadWriteLock.ReadLock readLock, ReentrantReadWriteLock.WriteLock writeLock){
        readLock.lock();
        System.out.println("获取读锁成功！");
        writeLock.lock();
        System.out.println("读锁升级写锁成功！");
    }

    public static void firstWriteSecondRead(ReentrantReadWriteLock.ReadLock readLock, ReentrantReadWriteLock.WriteLock writeLock){
        writeLock.lock();
        System.out.println("获取写锁成功！");
        readLock.lock();
        System.out.println("获取读锁成功！");
    }

    public static void firsReadSecondRead(ReentrantReadWriteLock.ReadLock readLock, ReentrantReadWriteLock.WriteLock writeLock){
        readLock.lock();
        System.out.println("获取读锁成功！");
        readLock.lock();
        System.out.println("获取读锁成功！");
    }
}
