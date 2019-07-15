package com.zzw.spring.boot.sentinel.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * 自定义listener
 *
 * @author zhaozhiwei
 * @date 2019/4/22 0:08
 */
@Slf4j
@Service
public class SaySomethingListener {

    @EventListener(value = SaySomethingEvent.class)
    public void onApplicationEvent(SaySomethingEvent event) {
        log.error(event.getMessage());
        log.warn(event.getMessage());
        log.info(event.getMessage());
    }

}
