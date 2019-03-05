package com.zzw.spring.cloud;

import com.zzw.spring.cloud.nacos.SpringBootNacosApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author zhaozhiwei
 * @date 2018/12/15 21:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootNacosApplication.class)
public class SpringCloudNacosTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}
