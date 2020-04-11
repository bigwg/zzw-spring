package com.zzw.spring.boot.base.domain;

import java.io.Serializable;

/**
 * @author zhaozhiwei
 * @date 2019/3/12 13:37
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1586577021184L;
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return this.age;
    }
}
