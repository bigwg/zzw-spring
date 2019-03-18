package com.zzw.spring.boot.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * @author zhaozhiwei
 * @date 2019/3/11 22:04
 */
@Component
@ManagedResource(
        objectName = "com.zzw.spring.boot.jmx:type=SimpleBean",
        description = "这是一个简单的Bean，被 Spring 托管"
)
public class SimpleBean {
    private Long id;
    private String name;
    private Integer value;

    @ManagedAttribute(description = "id属性")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManagedAttribute(description = "name属性")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManagedAttribute(description = "value属性")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @ManagedOperation(description = "显示全部属性")
    public String displayValue(){
        return toString();
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
