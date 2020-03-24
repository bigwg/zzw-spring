package com.zzw.data.structure;

import java.util.Arrays;

/**
 * 堆排序（大顶堆）
 *
 * @author zhaozhiwei
 * @date 2020/2/21 22:15
 */
public class HeapSort {

    /**
     * 交换交表位置
     *
     * @param a
     * @param i
     * @param j
     */
    public static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void buildHeap(int[] a, int start, int end) {
        if (start >= end) {
            return;
        }
        int i = start;
        while (i < end) {
            int left = 2 * i + 1;
            int right = left + 1;
            if (left > end) {
                return;
            }
            int be_exchange = left;
            if (right <= end && a[left] < a[right]) {
                be_exchange = right;
            }
            if (a[i] < a[be_exchange]) {
                swap(a, i, be_exchange);
                i = be_exchange;
            } else {
                return;
            }

        }
    }

    /**
     * 堆排序（大顶堆）
     *
     * @param a
     * @param start
     * @param end
     */
    public static void sort(int[] a, int start, int end) {
        for (int i = (end - start + 1) / 2 - 1; i >= 0; i--) {
            buildHeap(a, i, end);
        }
        for (int j = end; j > 0; j--) {
            swap(a, 0, j);
            buildHeap(a, 0, j - 1);
        }
    }

    public static void main(String[] args) {
        int[] a = {9, 1, 4, 3, 8, 0, 6, 2, 3, 10};
        sort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }
}
