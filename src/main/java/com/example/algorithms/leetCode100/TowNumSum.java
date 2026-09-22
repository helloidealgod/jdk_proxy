package com.example.algorithms.leetCode100;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * <p>
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
 * <p>
 * 你可以按任意顺序返回答案。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 * <p>
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 * <p>
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 * <p>
 * <p>
 * 提示：
 * <p>
 * 2 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * 只会存在一个有效答案
 * <p>
 * <p>
 * 进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？
 */
public class TowNumSum {
    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> numsIndex = new HashMap();
        numsIndex.put(nums[0], 0);
        for (int i = 1; i < nums.length; i++) {
            if (null == numsIndex.get(target - nums[i])) {
                numsIndex.put(nums[i], i);
            }else if (null != numsIndex.get(target - nums[i])) {
                result[0] = numsIndex.get(target - nums[i]);
                result[1] = i;
                return result;
            }
        }
        return null;
    }

    public static void main(String[] args) {
//        int[] nums = {2, 7, 11, 15};
//        twoSum(nums, 9);
//        int[] nums2 = {3,2,4};
//        twoSum(nums2, 6);
//        int[] nums3 = {3,3};
//        twoSum(nums3, 6);
        int[] nums4 = {2,5,5,11};
        twoSum(nums4, 10);
    }
}
