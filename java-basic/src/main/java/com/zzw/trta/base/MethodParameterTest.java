package com.zzw.trta.base;

/**
 * 参数传递
 *
 * @author zhaozhiwei
 * @date 2020/3/1 17:34
 */
public class MethodParameterTest {

    public static void test1(String str){
        str = "new string";
    }

    public static void main(String[] args) {
        String oldStr = "old string";
        test1(oldStr);
        System.out.println(oldStr);
    }
}
