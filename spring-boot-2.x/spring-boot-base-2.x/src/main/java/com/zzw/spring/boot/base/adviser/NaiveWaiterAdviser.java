package com.zzw.spring.boot.base.adviser;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * NaiveWaiter增强类
 *
 * @author zhaozhiwei
 * @date 2019/5/6 11:36
 */
@Slf4j
@Aspect
@Component
public class NaiveWaiterAdviser {

    @Before("execution(* greetTo(..))")
    public void beforeGreeting1(){
       log.info("How are you?");
    }

    @Before("execution(* greetTo(..))")
    public void beforeGreeting2(){
        log.info("I'm fine thanks!");
    }

    @Before("execution(* greetTo(..))")
    public void beforeGreeting3(){
        log.info("I'm fine thanks~~~~!");
    }
}
