package com.zzw.spring.boot.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class SpringBootDataApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringBootDataApplication.class, args);
    }
}
