package com.zzw.spring.boot.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

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
public class SpringBootSentinelApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootSentinelApplication.class, args);
    }
}
