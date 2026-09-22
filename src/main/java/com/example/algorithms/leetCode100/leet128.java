package com.example.algorithms.leetCode100;

import java.util.*;

public class leet128 {
    public static int longestConsecutive(int[] nums) {
        Map<Integer, Integer> map = new HashMap();
        Map<Integer, Integer> seeked = new HashMap();
        Map<Integer, Integer> passed = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (null == map.get(nums[i] - 1)) {
                int currentMax = 1;
                if (null != seeked.get(nums[i])) {
                    continue;
                }
                if (null != passed.get(nums[i])) {
                    continue;
                }
                passed.put(nums[i], 1);
                while (null != map.get(nums[i] + currentMax)) {
                    if (null != seeked.get(nums[i] + currentMax)) {
                        currentMax += seeked.get(nums[i] + currentMax);
                        break;
                    } else {
                        passed.put(nums[i] + currentMax, 1);
                        currentMax++;
                    }
                }
                seeked.put(nums[i], currentMax);
                if (currentMax > max) {
                    max = currentMax;
                }
            }
        }
        return max;
    }


    public static void main(String[] args) {
        //int[] nums = {100, 4, 200, 1, 3, 2};
//        int[] nums = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        int[] nums = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        longestConsecutive(nums);
    }
}
