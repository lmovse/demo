package info.lmovse.arithmetic;

import java.util.ArrayList;
import java.util.Random;

public class FindIndexCombine {
    /**
     * 在 4 千万正整数集合中找出符合 Ai + Aj = N 的所有索引  i j 组合。
     */
    public static void main(String[] args) {
        int[] arr = new int[100000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
        long old = System.currentTimeMillis();
        find(100, arr);
        System.out.println(System.currentTimeMillis() - old);
    }

    @SuppressWarnings("all")
    private static void find(int n, int[] arr) {

        int count = 0;
        for (int i = 1; i < n; i++) {

            int j = n - i;
            ArrayList<Integer> iList = new ArrayList<Integer>();
            ArrayList<Integer> jList = new ArrayList<Integer>();

            System.out.println(" 符合条件的表达式有: ");
            for (int index = 0; index < arr.length; index++) {
                if (arr[index] == i) {
                    iList.add(index);
                }
                if (arr[index] == j) {
                    jList.add(index);
                }
            }
            System.out.println("i 的值为: " + i + ",j 的值为 " + j);
            for (Integer ii : iList) {
                for (Integer jj : jList) {
                    System.out.println(ii + jj);
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
