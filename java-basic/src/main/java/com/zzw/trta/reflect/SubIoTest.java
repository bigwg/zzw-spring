package com.zzw.trta.reflect;


import com.zzw.trta.io.IoTest;
import com.zzw.trta.io.Test;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/7 下午4:05
 */
public class SubIoTest extends IoTest {

    public static void main(String[] args) {
        System.out.println(Test.class.isAssignableFrom(SubIoTest.class));
    }
}
