package od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        int[] nums = {4, 3, 6, 9, 7};
        alloc(nums, 3);
    }

    public static int alloc(int[] nums, int k) {
        // write code here
        int max = nums[0];
        if (k == nums.length) {
            for (int i = 0; i < nums.length; i++) {
                max = Math.max(max, nums[i]);
            }
            return max;
        }
        int[] result = new int[k];
        int[] indexs = new int[k];
        //求平均值
        int mid = 0;
        for (int i = 0; i < nums.length; i++) {
            mid += nums[i];
        }
        mid = mid / k;
        int size = 1;
        int index = 0;
        while (size < k) {
            int currSum = 0;
            for (int i = index; i < nums.length; i++) {
                if (currSum < mid && (k-size+1) < (nums.length-i)) {
                    currSum += nums[i];
                } else {
                    indexs[size] = i - 1;
                    result[size - 1] = currSum;
                    size++;
                    index = i;
                    mid = 0;
                    for (int j = index; i < nums.length; i++) {
                        mid += nums[i];
                    }
                    max = Math.max(max, currSum);
                    if (size < k) {
                        mid = mid / (k - size);
                    } else {
                        max = Math.max(max, mid);
                        result[size - 1] = mid;
                    }
                    break;
                }
            }
        }
        int max2 = 0;
        int max3 = 0;
        int temp = 0;
        int nv1 = 0;
        int nv2 = 0;
        for (int i = 0; i < result.length - 1; i++) {
//            if (result[i] > result[i + 1]) {
                do {
                    nv1 = result[i] - nums[indexs[i + 1]];
                    nv2 = result[i + 1] + nums[indexs[i + 1]];
                    max2 = Math.max(nv1, nv2);
                    if(max2 == temp){
                        break;
                    }
                    max3 = Math.max(result[i], result[i + 1]);
                    if (max2 < max3) {
                        result[i] = nv1;
                        result[i + 1] = nv2;
                        indexs[i + 1] = indexs[i + 1] - 1;
                    }
                    temp = max2;
                } while (max2 > max3);
//            }
        }
        max = result[0];
        for (int i = 0; i < result.length; i++) {
            max = Math.max(max, result[i]);
        }
        return max;
    }
}
