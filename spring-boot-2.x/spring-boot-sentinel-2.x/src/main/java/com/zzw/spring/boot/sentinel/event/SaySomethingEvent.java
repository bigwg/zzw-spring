package com.zzw.spring.boot.sentinel.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 *
 * @author zhaozhiwei
 * @date 2019/4/22 0:04
 */
public class SaySomethingEvent extends ApplicationEvent {

    @Getter
    private String message;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SaySomethingEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
