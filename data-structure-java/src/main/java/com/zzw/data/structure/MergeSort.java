package com.zzw.data.structure;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author zhaozhiwei
 * @date 2020/2/21 22:09
 */
public class MergeSort {

    public static void merge(int[] a, int[] tmp, int start, int mid, int end) {
        int i = start;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= end) {
            if (a[i] < a[j]) {
                tmp[k] = a[i];
                i++;
            } else {
                tmp[k] = a[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            tmp[k] = a[i];
            i++;
            k++;
        }
        while (j <= end) {
            tmp[k] = a[j];
            j++;
            k++;
        }
        for (int l = 0; l < k; l++) {
            a[start + l] = tmp[l];
        }
    }

    public static void split(int[] a, int[] tmp, int start, int end) {
        if (end <= start) {
            return;
        }
        int mid = (end + start) / 2;
        split(a, tmp, start, mid);
        split(a, tmp, mid + 1, end);
        merge(a, tmp, start, mid, end);
    }

    public static void main(String[] args) {
        int[] a = {9, 1, 4, 3, 8, 0, 6, 2, 3, 10};
        int[] temp = new int[10];
        split(a, temp, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

}
