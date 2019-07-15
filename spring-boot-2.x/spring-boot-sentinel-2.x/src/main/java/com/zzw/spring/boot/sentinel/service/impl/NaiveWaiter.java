package com.zzw.spring.boot.sentinel.service.impl;

import com.zzw.spring.boot.sentinel.service.Waiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Waiter实现类
 *
 * @author zhaozhiwei
 * @date 2019/5/6 11:32
 */
@Slf4j
@Service
public class NaiveWaiter implements Waiter {

    @Override
    public void greetTo(String clientName) {
        log.info("NaiveWaiter: greet to " + clientName + "...");
    }

    @Override
    public void serveTo(String clientName) {
        log.info("NaiveWaiter: serve to " + clientName + "...");
    }
}
