package com.example.algorithms.leetCode100;


import java.util.PriorityQueue;
import java.util.Stack;

public class leet239 {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int currentMax = nums[0];
        //int currentMaxIndex = 0;
        int[] result = new int[nums.length - k + 1];
        if(k == 1){
            return nums;
        }
        for (int i = 0; i < nums.length; i++) {
            if (i + k <= nums.length) {
                if (1 <= i) {
                    //新入的是否最大，出栈的是否是之前最大值
                    if (nums[i + k - 1] >= currentMax) {
                        currentMax = nums[i + k - 1];
                        result[i] = nums[i + k - 1];
                    } else if (currentMax == result[i - 1]) {
                        int max = nums[i];
                        for (int j = i; j < i + k; j++) {
                            max = Math.max(max, nums[j]);
                        }
                        result[i] = max;
                        currentMax = max;
                        // = Math.max(currentMax, max);
                    } else {
                        result[i] = currentMax;
                    }
                } else {
                    int max = nums[i];
                    for (int j = i; j < i + k; j++) {
                        max = Math.max(max, nums[j]);
                    }
                    result[i] = max;
                    currentMax = Math.max(currentMax, max);
                }
            }
        }
//        int max = nums[0];
//        int[] result = new int[nums.length - k + 1];
//        for(int i=0;i<k;i++){
//            max = Math.max(max,nums[i]);
//        }
//        result[0] = max;
//        for(int i=1;i<nums.length;i++){
//            if(i+k <= nums.length){
//                if(result[i-1] == nums[i-1]){
//                    max = nums[i];
//                    for(int j=i;j<i+k;j++){
//                        max = Math.max(max,nums[i]);
//                    }
//                    result[i] = max;
//                }else {
//                    max = Math.max(result[i - 1], nums[i + k - 1]);
//                    result[i] = max;
//                }
//            }
//        }
        return result;
    }

    public static void main(String[] args) {
//        int[] nums = {7, 2, 4};
//        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
//        maxSlidingWindow(nums, 3);
        int[] nums = {9,10,9,-7,-4,-8,2,-6};
        maxSlidingWindow(nums, 5);
    }
}
