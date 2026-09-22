package com.example.algorithms.leetCode100;

import java.util.*;

public class leet72 {
    public int minDistance(String word1, String word2) {
        int[][] table = new int[word1.length()+1][word2.length()+1];
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();
        for(int i=0;i<=word1.length();i++){
            table[i][0] = i;
        }
        for(int i=0;i<=word2.length();i++){
            table[0][i] = i;
        }
        for(int i=1;i<=word1.length();i++){
            for(int j=1;j<=word2.length();j++){
                if(chars1[i-1] == chars2[j-1]){
                    table[i][j] = table[i-1][j-1];//当前字符相同，不需要操作
                }else{
                    table[i][j] = Math.min(
                            Math.min(table[i-1][j-1],table[i-1][j]),
                            table[i][j-1]
                    ) + 1;
                }
            }
        }
        return table[word1.length()][word2.length()];
    }
}
