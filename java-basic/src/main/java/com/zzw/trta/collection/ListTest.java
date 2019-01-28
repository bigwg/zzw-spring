package com.zzw.trta.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaozhiwei
 * @date 2018/12/22 14:09
 */
public class ListTest {
    private String element;

    public ListTest(String element){
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(String.valueOf(i));
        }
        List<String> copy = new ArrayList<>(list);
        for (String s : copy) {
            System.out.println(s);
        }
    }
}
