package com.zzw.spring.boot.base.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * 自定义listener
 *
 * @author zhaozhiwei
 * @date 2019/4/22 0:08
 */
@Service
public class SaySomethingListener {

    @EventListener(value = SaySomethingEvent.class)
    public void onApplicationEvent(SaySomethingEvent event) {
        System.out.println(event.getMessage());
    }

}
