package com.core;

import java.util.ArrayList;

/**
 * Created by recycle on 12/16 0016.
 */
public class GeneralFunc {
    public static int randomInt(int[] arrays) {
        if(arrays.length == 0)
            return 0;
        double r = Math.random();
        int i = 0;
        double n = 1.0/arrays.length;
        while(r > n * i++);
        return arrays[i-2];
    }

    public static int[] randomArrays(int n) {
        int[] arrays = new int[n];
        for(int i = 0; i < n; i++) {
            arrays[i] = i + 1;
        }
        int j;
        int temp;
        for(int i = 0; i < n; i++) {
            j = randomInt(arrays) - 1;
            temp = arrays[i];
            arrays[i] = arrays[j];
            arrays[j] = temp;
        }
        return arrays;
    }

    public static int[] randomArrays2(int n1, int n2) {
        int[] arrays2 = new int[n2];
        int flag = 0;
        if(n1 != 0) {
            for(int i = 0; i <= (n2 - 1)/n1; i++) {
                int[] arrays1 = randomArrays(n1);
                for(int j = 0; j < n1; j++) {
                    arrays2[flag] = arrays1[j];
                    flag++;
                    if(flag >= n2){
                        break;
                    }
                }
            }
        }
        return arrays2;
    }

    public static int randomTime(int[] arrays, int n) {
        //数组列表赋值自然数
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            integerArrayList.add(new Integer(i + 1));
        }
        //数组列表移除与数组相同的元素
        for(int a : arrays) {
            for(int i = 0; i < integerArrayList.size(); i++) {
                if(a == Integer.valueOf(integerArrayList.get(i))) {
                    integerArrayList.remove(i);
                    i--;
                }
            }
        }
        //数组列表转化为数组
        int[] arrays1 = new int[integerArrayList.size()];
        for(int i = 0; i < integerArrayList.size(); i++) {
            arrays1[i] = Integer.valueOf(integerArrayList.get(i));
        }
        return randomInt(arrays1);
    }
}
