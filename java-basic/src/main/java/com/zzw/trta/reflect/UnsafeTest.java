package com.zzw.trta.reflect;

import lombok.Getter;
import lombok.Setter;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zhaozhiwei
 * @date 2019/2/28 14:28
 */
public class UnsafeTest {

    @Getter
    @Setter
    private Integer value;

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        Class clazz = Unsafe.class;
        Unsafe unsafe = null;
        for (Field f : clazz.getDeclaredFields()) {
            if ("theUnsafe".equals(f.getName())) {
                f.setAccessible(true);
                unsafe = (Unsafe) f.get(null);
            }
        }
        UnsafeTest ut = new UnsafeTest();
        ut.setValue(100);
        System.out.println(ut.getValue());
        System.out.println(unsafe);
        long valueOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("value"));
//        unsafe.getAndSetInt(ut, valueOffset, 1);
//        unsafe.compareAndSwapInt(ut, valueOffset, 100, 200);
        unsafe.compareAndSwapObject(ut, valueOffset, 100, 200);
        System.out.println(ut.getValue());
    }
}
