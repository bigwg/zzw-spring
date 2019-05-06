package com.zzw.spring.boot.base;

import com.zzw.spring.boot.base.event.SaySomethingEvent;
import com.zzw.spring.boot.base.service.Waiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author zhaozhiwei
 * @desc SpringBoot启动类
 * @date 2018/9/24 上午12:00
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@ImportResource(locations = {
        "classpath:META-INF/spring/context.xml",
        "classpath:META-INF/spring/context-test.xml"}
)
public class SpringBootBaseApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootBaseApplication.class, args);
        SaySomethingEvent event = new SaySomethingEvent(applicationContext, "赵志伟，你好！");
        applicationContext.publishEvent(event);
        Waiter naiveWaiter = applicationContext.getBean("naiveWaiter", Waiter.class);
        naiveWaiter.greetTo("赵志伟");
        System.out.println(naiveWaiter.getClass().getSimpleName());
    }
}
