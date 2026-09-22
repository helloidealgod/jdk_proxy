package com.example.algorithms.leetCode100;

import java.util.Scanner;

public class leet11 {
    public static int maxArea(int[] height) {
        int maxArea = 0;
        int currentMax = 0;
        int head = 0;
        int tail = height.length - 1;
        while (head < tail) {
            currentMax = (tail - head) * Math.min(height[head], height[tail]);
            maxArea = Math.max(maxArea, currentMax);
            if (height[head] < height[tail]) {
                head++;
            } else {
                tail--;
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
//        int[] nums = {0, 1, 0, 3, 12};
        //int[] nums = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        Scanner scanner = new Scanner(System.in);
        int nextInt = scanner.nextInt();
        int[] nums = new int[nextInt];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }
        int maxArea = maxArea(nums);
        System.out.println("maxArea1=" + maxArea);
        int[] nums2 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        maxArea = maxArea(nums2);
        System.out.println("maxArea2=" + maxArea);
    }
}
