package com.zzw.data.structure;

import java.util.Arrays;

/**
 * 单轴快排
 *
 * @author zhaozhiwei
 * @date 2020/2/21 22:17
 */
public class PivotQuickSort {

    /**
     * 快速排序（单轴）
     *
     * @param a
     * @param start
     * @param end
     */
    public static void sort(int[] a, int start, int end) {
        int i = start;
        int j = end;
        int tmp = a[start];
        if (i >= j) {
            return;
        }
        while (i < j) {
            while (i < j && a[j] > tmp) {
                j--;
            }
            a[i] = a[j];
            while (i < j && a[i] <= tmp) {
                i++;
            }
            a[j] = a[i];
        }

        a[i] = tmp;
        sort(a, start, i - 1);
        sort(a, i + 1, end);
    }

    public static void main(String[] args) {
        int[] a = {9, 1, 4, 3, 8, 0, 6, 2, 3, 10};
        sort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }
}
