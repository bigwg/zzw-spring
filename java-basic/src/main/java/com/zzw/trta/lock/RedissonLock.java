package com.zzw.trta.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * TODO
 *
 * @author zhaozhiwei
 * @date 2020/3/30 20:12
 */
public class RedissonLock {

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create();
        RLock fairLock = redissonClient.getFairLock("");

    }
}
