package odtest;

/**
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
 * <p>
 * 示例 1:
 * 输入: nums = [-1,0,3,5,9,12], target = 9
 * 输出: 4
 * 解释: 9 出现在 nums 中并且下标为 4
 */
public class Test002 {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;
        boolean hasTarget = false;
        for (int i = 0; i < nums.length; i++) {
            if (target == nums[i]) {
                System.out.println(i);
                hasTarget = true;
            }
            if (target < nums[i]) {
                break;
            }
        }
        if (!hasTarget) {
            System.out.println("-1");
        }
        hasTarget = false;
        int left = 0;
        int right = nums.length - 1;
        int mid = right / 2;
        while (left + 1 < right) {
            mid = left + (right - left) / 2;
            if (target > nums[mid]) {
                left = mid;
            } else if (target < nums[mid]) {
                right = mid;
            } else {
                System.out.println(mid);
                left++;
                hasTarget = true;
                //break;
            }
        }
        if (!hasTarget) {
            System.out.println("-1");
        }
    }
}
