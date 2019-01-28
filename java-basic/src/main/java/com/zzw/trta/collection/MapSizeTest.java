package com.zzw.trta.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/8 下午1:47
 */
public class MapSizeTest {
    public static void main(String[] args) {
        long count = 0L;
        for (int i = 0; i < 10000; i++) {
            Map<String, String> map = new HashMap<>(2);
            long once = 0L;
            long begin = System.nanoTime();
            map.put("a", "aa");
            map.put("b", "bb");
            System.out.println("once time:" + (once = (System.nanoTime() - begin)));
            count += once;
        }
        System.out.println("average time:" + count / 100);
    }
}
