package com.zzw.spring.boot.data.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhaozhiwei
 */
@Getter
@Setter
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1557122452343L;

    private Integer id;
    private String name;
    private String sex;
    private Integer age;
}
