package com.zzw.trta.inheritance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhaozhiwei
 * @date 2018/10/16 10:42
 */
@Getter
@Setter
@ToString
public class ParentClass {

    private String name;
    private Integer parentAge;
    private String parentSex;

    public void setAgeAndSex(Integer age, String sex) {
        this.name = "父亲";
        this.parentAge = age;
        this.parentSex = sex;
    }

    public void printAgeAndSex() {
        System.out.println("name: " + name + " age: " + parentAge + " sex: " + parentSex);
    }
}
