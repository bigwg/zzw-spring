package com.zzw.spring.boot.base;

import com.zzw.spring.boot.base.controller.DemoController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * @author zhaozhiwei
 * @desc SpringBoot启动类
 * @date 2018/9/24 上午12:00
 */
@SpringBootApplication
@ImportResource(locations = {
        "classpath:META-INF/spring/context.xml",
        "classpath:META-INF/spring/context-test.xml"}
)
@MapperScan("com.zzw.spring.boot.base.dao")
public class SpringBootBaseApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootBaseApplication.class, args);
//        SaySomethingEvent event = new SaySomethingEvent(applicationContext, "赵志伟，你好！");
//        applicationContext.publishEvent(event);
        DemoController demoController = applicationContext.getBean("demoController", DemoController.class);
        System.out.println(demoController.getPerson());

    }
}
