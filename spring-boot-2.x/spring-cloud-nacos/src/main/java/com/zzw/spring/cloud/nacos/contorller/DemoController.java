package com.zzw.spring.cloud.nacos.contorller;

import com.zzw.spring.cloud.nacos.common.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/30 21:25
 */
@Slf4j
@RestController
@RefreshScope
@RequestMapping("config")
public class DemoController {

    @Value(value = "${useLocalCache}")
    private Boolean useLocalCache;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO<Boolean> get() {
        return ResultDTO.handleSuccess(useLocalCache);
    }
}
