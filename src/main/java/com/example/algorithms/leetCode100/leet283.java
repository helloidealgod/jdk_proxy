package com.example.algorithms.leetCode100;

public class leet283 {
    public static void moveZeroes(int[] nums) {
        int zoreIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            if (0 == nums[i]) {
                zoreIndex = i;
                break;
            }
        }
        if(zoreIndex < 0){
            return;
        }
        for (int i = zoreIndex + 1; i < nums.length; i++) {
            if (Math.abs(nums[i]) > nums[zoreIndex]) {
                nums[zoreIndex] = nums[i];
                nums[i] = 0;

                for (int j = zoreIndex; j < nums.length; j++) {
                    if (0 == nums[j]) {
                        zoreIndex = j;
                        i = j;
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
//        int[] nums = {0, 1, 0, 3, 12};
        int[] nums = {0,1,1,0};
        moveZeroes(nums);
    }
}
