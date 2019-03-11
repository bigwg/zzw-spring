package com.zzw.spring.boot.contorller;

import com.zzw.spring.boot.jmx.SimpleBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/30 21:25
 */
@Slf4j
@RestController
public class DemoController {

    @Autowired
    private SimpleBean simpleBean;

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
}
