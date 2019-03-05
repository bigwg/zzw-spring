package com.zzw.spring.cloud.nacos.contorller;

import com.zzw.spring.cloud.nacos.common.ResultDTO;
import com.zzw.spring.cloud.nacos.domain.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

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
    public ResultDTO<Boolean> get() {
        return ResultDTO.handleSuccess(useLocalCache);
    }

    @RequestMapping(value = "/getString", method = RequestMethod.GET)
    public ResultDTO<Message> getString(@RequestParam("str") String value) {
        if (StringUtils.isBlank(value)){
            return ResultDTO.handleError("not null");
        }
        Message message = new Message();
        message.setMessage(value);
        return ResultDTO.handleSuccess(message);
    }

}
