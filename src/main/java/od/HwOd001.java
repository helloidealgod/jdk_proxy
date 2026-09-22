package od;

import java.util.Arrays;

/**
 * 给n个数组，每个数组里存起止下标，如何求这些数据无交集的最大数组个数
 * 这是一个经典的区间调度问题（Interval Scheduling Maximization），核心是求最多能选出多少个互不重叠的区间。经典解法是贪心算法：按每个区间的结束时间从小到大排序，然后依次选择，确保所选区间互不重叠。
 *
 * 解题思路
 * 排序：将所有区间按 end 值升序排序。
 * 贪心选择：初始化 currentEnd = -∞，遍历排序后的区间：
 * 如果当前区间的 start >= currentEnd，则选择该区间，并更新 currentEnd = end。
 * 否则，跳过该区间（因为它与之前选择的区间重叠）
 */
public class HwOd001 {
    public static int maxNonOverlapping(int[][] intervals) {
        // 按结束时间升序排序
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int count = 0;
        int lastEnd = Integer.MIN_VALUE;

        for (int[] interval : intervals) {
            //if (interval[0] >= lastEnd) {
            if (interval[0] > lastEnd) {
                count++;
                lastEnd = interval[1];
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[][] intervals = {
                {1,3},
                {2,2},
                {3,3},
                {4,4}
        };
        maxNonOverlapping(intervals);
    }
}
