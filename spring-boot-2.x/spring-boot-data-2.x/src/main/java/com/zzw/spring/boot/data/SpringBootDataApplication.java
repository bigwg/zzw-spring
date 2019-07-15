package com.zzw.spring.boot.data;

import com.zzw.spring.boot.data.domain.model.User;
import com.zzw.spring.boot.data.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * @author zhaozhiwei
 * @desc SpringBoot启动类
 * @date 2018/9/24 上午12:00
 */
@SpringBootApplication
@EnableConfigurationProperties
public class SpringBootDataApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootDataApplication.class, args);
//        UserService userService = applicationContext.getBean("userService", UserService.class);
//        User user = new User();
//        user.setName("zzw");
//        user.setAge(18);
//        user.setSex("男");
//        userService.addUser(user);
    }
}
