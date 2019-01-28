package com.zzw.trta.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhaozhiwei
 * @date 2018/12/22 14:10
 */
public class IteratorTest {

    public ListTest getListTest(){
        return new ListTest("1"){
            @Override
            public String getElement(){
                return "1";
            }
        };
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(String.valueOf(i));
        }
        list.removeIf(s -> s.equals("6"));
        for (String s : list){
            System.out.println(s);
        }
    }
}
