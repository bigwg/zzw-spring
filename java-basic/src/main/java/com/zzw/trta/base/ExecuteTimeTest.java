package com.zzw.trta.base;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/19 下午4:27
 */
public class ExecuteTimeTest {

    private static int test() {
        int sum = 0;
        for (int i = 0; i < 100000000; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        test();
        System.out.println(System.currentTimeMillis() - begin);
    }
}
