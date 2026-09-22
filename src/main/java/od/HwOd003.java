package od;

/**
 * 给一个数组，要划分为你个子数组，要求每个子数组之合最最平均
 * 这是一个经典的数组划分问题，目标是让划分后的所有子数组和尽可能平均。通常有两种理解：
 * 精确平均划分：是否能将数组划分为 k 个子数组，使得每个子数组和相等 → 这是划分等和子集问题，需要判断可行性。
 * 近似平均划分：划分成 k 个子数组，使得子数组和之间的最大差值最小 → 这是负载均衡（平衡划分）问题，需要用二分 + 贪心。
 * 根据你“最平均”的描述，大概率是第二类：让所有子数组的和尽量接近。
 * 解题思路（二分 + 贪心）
 * 给定一个数组 nums 和划分数量 k，求一种划分方案，使得所有子数组和的最大值最小。
 * 核心思路：
 * 二分答案：猜测一个最大子数组和 mid，检查能否在不超过 mid 的前提下，将数组划分成 k 个连续子数组。
 * 贪心检查：从左到右累加，如果当前子数组和超过 mid，则开始新一段。
 */
public class HwOd003 {
    public static int splitArray(int[] nums, int k) {
        int left = 0;
        int right = 0;
        for (int num : nums) {
            left = Math.max(left, num);
            right += num;
        }

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canSplit(nums, k, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private static boolean canSplit(int[] nums, int k, int maxSum) {
        int count = 1;
        int currentSum = 0;
        for (int num : nums) {
            if (currentSum + num > maxSum) {
                count++;
                currentSum = num;
                if (count > k) {
                    return false;
                }
            } else {
                currentSum += num;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] nums = {4, 3, 6, 9, 7};
        splitArray(nums, 2);
    }
}
