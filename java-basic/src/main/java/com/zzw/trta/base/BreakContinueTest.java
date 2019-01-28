package com.zzw.trta.base;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/14 下午12:19
 */
public class BreakContinueTest {

    public static void main(String[] args) {
        loop1:
        for (int i = 0; i < 5; i++) {
//            loop2:
            for (int x = 0; x < 5; x++) {
                System.out.println("i=" + i + "x=" + x);
                if (x==2){
//                    break loop1;
                    continue loop1;
                }
            }
        }
    }
}
