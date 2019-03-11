package com.zzw.spring.cloud.nacos.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @author zhaozhiwei
 * @date 2019/3/10 15:06
 */
@Slf4j
public class Logging {

    public static void main(String[] args) {
        MDC.put("traceId", "main");
        log.info("Hello, World!");
    }
}
