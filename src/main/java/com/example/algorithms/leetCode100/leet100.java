package com.example.algorithms.leetCode100;

public class leet100 {
    public static int coinChange(int[] coins, int amount) {
        int[] counts = new int[amount + 1];
        counts[0] = 0;

        for (int i = 1; i <= amount; i++) {
            Integer minCount = null;
            for (int j = 0; j < coins.length; j++) {
                if (i >= coins[j]) {
                    if (i - coins[j] >= 0) {
                        if(0 > counts[i - coins[j]]){

                        }else if (null == minCount) {
                            minCount = 1 + counts[i - coins[j]];
                        } else {
                            minCount = Math.min(1 + counts[i - coins[j]], minCount);
                        }
                    }
                }
            }
            if (null == minCount || 0 == minCount) {
                counts[i] = -1;
            } else {
                counts[i] = minCount;
            }
        }
        return counts[amount];
    }

    public static void main(String[] args) {
//        int[] nums = {1, 2, 5};
//        coinChange(nums, 11);
        int[] nums = {186, 419, 83, 408};
        coinChange(nums, 6249);
    }
}
