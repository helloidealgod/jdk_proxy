package com.example.algorithms.leetCode100;

import java.util.ArrayDeque;
import java.util.Deque;

public class leet739 {
    public static int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        result[result.length - 1] = 0;
        Deque<Integer> tIndex = new ArrayDeque();
        tIndex.offerLast(result.length - 1);
        for (int i = result.length - 2; i >= 0; i--) {
            //从队列中找第一个大于当前值的温度
            while (!tIndex.isEmpty() && temperatures[i] >= temperatures[tIndex.peekLast()]) {
                tIndex.pollLast();
            }
            if (tIndex.isEmpty()) {
                result[i] = 0;
                tIndex.offerLast(i);
            } else {
                result[i] = tIndex.peekLast() - i;
                tIndex.offerLast(i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {73, 74, 75, 71, 69, 72, 76, 73};
        dailyTemperatures(nums);
    }
}
