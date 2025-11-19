package com.example.algorithms.coin;

public class Main {
    public static void main(String[] args) {
        int[] coinAmount = {1, 2, 5};
        int amount = 11;
        int[] coins = new int[amount + 1];
        coins[0] = 0;
        for (int i = 1; i <= amount; i++) {
            int minCoins = Integer.MAX_VALUE;
            for (int j = 0; j < coinAmount.length; j++) {
                if (i - coinAmount[j] >= 0) {
                    minCoins = Math.min(minCoins, coins[i - coinAmount[j]] + 1);
                }
            }
            coins[i] = minCoins;
        }
        System.out.println("要组合出总额为" + amount + "的最少硬币数是：" + coins[amount]);

        int[][] coins2 = new int[amount + 1][4];
        coins2[0][0] = 0;
        coins2[0][1] = 0;
        coins2[0][2] = 0;
        coins2[0][3] = 0;
        for (int i = 1; i <= amount; i++) {
            int[] minCoins = new int[4];
            minCoins[0] = Integer.MAX_VALUE;
            for (int j = 0; j < coinAmount.length; j++) {
                if (i - coinAmount[j] >= 0) {
                    if (minCoins[0] > coins2[i - coinAmount[j]][0] + 1) {
                        minCoins[0] = coins2[i - coinAmount[j]][0] + 1;
                        minCoins[1] = coins2[i - coinAmount[j]][1];
                        minCoins[2] = coins2[i - coinAmount[j]][2];
                        minCoins[3] = coins2[i - coinAmount[j]][3];
                        minCoins[j + 1] = minCoins[j + 1] + 1;
                    }
                }
            }
            coins2[i] = minCoins;
        }
        System.out.println("要组合出总额为" + amount + "的最少硬币数是：" + coins2[amount][0]);
        System.out.print("组合方式：");
        for (int i = 0; i < coinAmount.length; i++) {
            System.out.print(coins2[amount][i + 1] + "枚" + coinAmount[i] + "元 ");
        }
        System.out.println("");
    }
}
