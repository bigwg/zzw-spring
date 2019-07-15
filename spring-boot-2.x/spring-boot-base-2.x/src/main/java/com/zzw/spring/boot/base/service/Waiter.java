package com.zzw.spring.boot.base.service;

/**
 * Waiter接口
 *
 * @author zhaozhiwei
 * @date 2019/5/6 10:57
 */
public interface Waiter {
    void greetTo(String clientName);
    void serveTo(String clientName);
}
