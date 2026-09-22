package com.example.algorithms.leetCode100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class leet15 {
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int left = 1;
        int right = nums.length - 1;
        int k = 0;
        List<List<Integer>> result = new ArrayList();
        List<Integer> res = null;
        for (int i = 0; i < nums.length; i++) {
            //退化为2数之和 = k
            k =  -nums[i];
            while (left < right) {
                if (k > nums[left] + nums[right]) {
                    left++;
                } else if (k < nums[left] + nums[right]) {
                    right--;
                } else {
                    if(i != left && i!= right && left != right){

                        if(null == res || !(nums[i] == res.get(0)
                                && nums[left] == res.get(1)
                                && nums[right] == res.get(2)
                        )
                        ){
                            res = new ArrayList<>();
                        }
                        if(null != res && res.isEmpty()){
                            res.add(nums[i]);
                            res.add(nums[left]);
                            res.add(nums[right]);

                            result.add(res);
                        }
                    }
                    left++;
                }
            }
            left = i + 2;
            right = nums.length - 1;
            if (null != res && !res.isEmpty()) {
                result.add(res);
            }
        }
        result = result.stream().distinct().collect(Collectors.toList());
        return result;
    }

    public static void main(String[] args) {
//        int[] nums = {-1, 0, 1, 2, -1, -4};
        int[] nums = {-2,0,1,1,2};
        threeSum(nums);
    }
}
