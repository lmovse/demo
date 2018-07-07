package info.lmovse.arithmetic;

import java.util.Random;

public class QuickSort {
    public static void main(String[] args) {
        Integer[] arr = new Integer[2];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Random().nextInt(100);
        }
        long start = System.currentTimeMillis();
        max(arr);
        quickSort(arr, 0, arr.length - 1);
        long end = System.currentTimeMillis();
        System.out.println(" 数组大小：" + arr.length + ";" + (end - start) + " 毫秒 ");

    }

    private static void quickSort(Comparable[] arr, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        // 排序并获取切分点
        int point = split(arr, lo, hi);
        // 左半部分排序
        quickSort(arr, lo, point - 1);
        // 右半部分排序
        quickSort(arr, point + 1, hi);
    }

    private static int split(Comparable[] arr, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            // 找到最近的一个比 a[lo] 大的下标索引，顺序为从左往右。a.length -1 索引下的元素为最大值，可以当哨兵，保证不越界
            while (arr[++i].compareTo(arr[lo]) < 0) {
            }
            // 找到最近的一个比 a[lo] 小的下标索引，顺序为从右往左。lo 索引下的元素为 point，可以当哨兵，保证不越界
            while (arr[lo].compareTo(arr[--j]) < 0) {
            }
            // 如果在比较的过程中发生 i >= j，即可以认为 一次查询已经结束，索引 j 右侧的元素都大于 a[lo]，左侧的元素都小于 a[lo]
            if (i >= j) {
                break;
            }
            // 将两者位置交换，保证索引 i 左侧的元素都小于 a[lo]，索引 j 右侧的元素都大于 a[lo]
            exch(arr, i, j);
        }
        exch(arr, lo, j);
        return j;
    }

    private static void exch(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void max(Comparable[] arr) {
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[0]) > 0) {
                index = i;
            }
        }
        exch(arr, index, arr.length - 1);
    }

}
