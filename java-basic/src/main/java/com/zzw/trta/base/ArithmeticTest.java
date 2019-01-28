package com.zzw.trta.base;

/**
 * @author zhaozhiwei
 * @desc 算法测试类
 * @date 2018/9/30 13:49
 */
public class ArithmeticTest {

    public static void main(String[] args) {
//        System.out.println(84170 & 255);
        System.out.println(111111 << 4);
        System.out.println(Integer.toBinaryString(111111 << 4));
//        System.out.println("1111111111111111111111111111111".length());
        String item = "mryt_order_0.t_order_[0-99]:order_no";
        int left = item.indexOf("[");
        int right = item.indexOf("]");
        // kafka分区hash配置伪正则
        if (left != -1 && right != -1) {
            int i = item.indexOf(":");
            String pk = item.substring(i + 1).trim();
            String dbTable = item.substring(0, left);
            String regex = item.substring(left + 1, right);
            String[] gap = regex.split("-");
            for (int j = Integer.valueOf(gap[0]); j <= Integer.valueOf(gap[1]); j++) {
                System.out.println(dbTable + j);
            }
        }
    }
}
