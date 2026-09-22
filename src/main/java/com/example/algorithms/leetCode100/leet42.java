package com.example.algorithms.leetCode100;

import java.util.*;

public class leet42 {
    public static int trap(int[] height) {
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];
        leftMax[0] = height[0];
        rightMax[height.length-1] = height[height.length-1];
        for(int i=1;i<height.length;i++){
            leftMax[i] = Math.max(height[i],leftMax[i-1]);
        }
        for(int i=height.length-1;i>0;i--){
            rightMax[i-1] = Math.max(height[i-1],rightMax[i]);
        }
        int maxV = 0;
        for(int i=0;i<height.length;i++){
            if(height[i] < Math.min(leftMax[i],rightMax[i])){
                maxV += Math.min(leftMax[i],rightMax[i]) - height[i];
            }
        }
        return maxV;
    }
    public static void main(String[] args) {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        trap(height);
    }
}
