package com.zzw.spring.boot.base.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 增强测试类
 *
 * @author zhaozhiwei
 * @date 2019/9/5 2:05 下午
 */
@Slf4j
@Aspect
@Component
public class DemoControllerAspect {

    @Around(value = "execution(* com.zzw.spring.boot.base.controller.DemoController.getPerson(..))")
    public Object getPersonAround(ProceedingJoinPoint pjp) {
        Object result = null;
        log.info("before com.zzw.spring.boot.base.controller.DemoController.getPerson...");
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        log.info("after com.zzw.spring.boot.base.controller.DemoController.getPerson...");
        return result;
    }

    @Before(value = "execution(* com.zzw.spring.boot.base.controller.DemoController.getPerson(..))")
    public void getPersonBefore(){
        log.info("getPersonBefore before com.zzw.spring.boot.base.controller.DemoController.getPerson...");
    }

}
