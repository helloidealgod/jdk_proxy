package com.example.algorithms.leetCode100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class leet560 {
    public static int subarraySum(int[] nums, int k) {
        //Arrays.sort(nums);
        int count = 0;
        int left = 0;
        int right = 0;
        int currSum = 0;
        while (right <= nums.length) {
            if (currSum < k) {
                if (right < nums.length) {
                    currSum += nums[right];
                }
                right++;
            } else if (currSum > k) {
                currSum -= nums[left];
                left++;
            } else {
                count++;
                currSum -= nums[left];
                left++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
//        int[] nums = {1, 1, 1}; 2
//        int[] nums = {1,2,3};
        int[] nums = {1,2,1,2,1};
        subarraySum(nums, 3);
    }
}
