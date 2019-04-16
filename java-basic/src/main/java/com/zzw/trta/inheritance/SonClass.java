package com.zzw.trta.inheritance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhaozhiwei
 * @date 2018/10/16 10:43
 */
@Getter
@Setter
@ToString
public class SonClass extends ParentClass {

    private String name;

    public SonClass(){
        setAgeAndSex(49, "男");
    }

    public String getNameString(){
        System.out.println(this.getClass().getSimpleName());
        return name;
    }

    public static void main(String[] args) {
        SonClass son = new SonClass();
        son.printAgeAndSex();
        son.getNameString();
        System.out.println(son.getParentSex());
    }
}
