package com.zzw.spring.boot.contorller;

import com.zzw.spring.boot.SpringBootBaseApplication;
import com.zzw.spring.boot.domain.Person;
import com.zzw.spring.boot.jmx.SimpleBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/30 21:25
 */
@Slf4j
@RestController
//@ConditionalOnBean(annotation = {Configurable.class})
public class DemoController {

    @Autowired
    private SimpleBean simpleBean;
    @Resource
    private Person person;
    @Value("${zzw.config}")
    private String config;

    @GetMapping("jmx/simple-bean")
    public SimpleBean simpleBean(@RequestParam(required = false) Long id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) Integer value) {
        if (id != null) {
            simpleBean.setId(id);
        }
        if (name != null) {
            simpleBean.setName(name);
        }
        if (value != null) {
            simpleBean.setValue(value);
        }
        return simpleBean;
    }

    @GetMapping("config/getPerson")
    public Person getPerson(){
        return person;
    }

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = SpringBootBaseApplication.class.getClassLoader();

        Enumeration<URL> resources = classLoader.getResources("META-INF/spring.factories");
        while (resources.hasMoreElements()) {
            System.out.println(resources.nextElement());
        }
    }
}
