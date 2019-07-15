package com.zzw.spring.boot.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.xml.transform.Source;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;

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
public class SpringBootDataApplication {

    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
//        SpringApplication.run(SpringBootDataApplication.class, args);
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources("classpath*:com/zzw/spring/boot/data/**/*.class");
//        for (Resource resource : resources){
//            System.out.println("------------" + resource.getURI());
//        }
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        Method[] declaredMethods = operatingSystemMXBean.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String methodName = declaredMethod.getName();
            if (methodName.startsWith("get")) {
                declaredMethod.setAccessible(true);
                System.out.println(methodName + " : " + declaredMethod.invoke(operatingSystemMXBean));
            }
        }
    }
}
