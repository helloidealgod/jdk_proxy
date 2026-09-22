package com.example.algorithms.leetCode100;

import java.util.ArrayList;
import java.util.List;

public class leet54 {
    public static List<Integer> spiralOrder(int[][] matrix) {
        int minTop=0;
        int maxBottom=matrix.length;
        int minLeft=0;
        int maxRight=matrix[0].length;
        int i=0,j=0;
        int direct = 0;
        List<Integer> result = new ArrayList();
        while(result.size() < matrix.length*matrix[0].length){
            result.add(matrix[i][j]);
            if(0 == direct){//向右
                if(j+1 < maxRight){
                    j++;
                } else {
                    direct = 1;//变化方向
                    minTop++;
                    if(i+1 < maxBottom){
                        i++;
                    }
                }
            }else if(1 == direct){//向下
                if(i+1 < maxBottom){
                    i++;
                } else {
                    direct = 2;//变化方向
                    maxRight--;
                    if(j-1 > minLeft){
                        j--;
                    }
                }
            }else if(2 == direct){//向左
                if(j-1 > minLeft){
                    j--;
                }else{
                    direct = 3;//变化方向
                    maxBottom--;
                    if(i-1 > minTop){
                        i--;
                    }
                }
            }else if(3 == direct){//向上
                if(i-1 > minTop){
                    i--;
                }else {
                    direct = 0;//变化方向
                    minLeft++;
                    if(j+1 < maxRight){
                        j++;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        int[] nums = {0, 1, 0, 3, 12};
        int[][] nums = {{1,2,3},{4,5,6},{7,8,9}};
        spiralOrder(nums);
    }
}
