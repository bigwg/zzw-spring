package com.zzw.spring.boot.base.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 学生实体
 *
 * @author zhaozhiwei
 * @date 2020/2/23 21:49
 */
@Getter
@Setter
@ToString
public class Student implements Serializable {

    private static final long serialVersionUID = 1582465742178L;

    /**
     * 主键
     */
    private Integer id;

    private String name;

    /**
     * 0女生 1男生
     */
    private Integer sex;
}
