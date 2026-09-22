package com.example.algorithms.leetCode100;


import java.util.Stack;

public class leet32 {
    public static int longestValidParentheses(String s) {
        char[] cs = s.toCharArray();
        int maxLength = 0;
        int currentMax = 0;
        Stack sybStack = new Stack();
        Stack intStack = new Stack();
        for (int i = 0; i < cs.length; i++) {
            if ('(' == cs[i]) {
                sybStack.push(cs[i]);
                intStack.push(i);
            } else {
                if (!sybStack.isEmpty() && '(' == (char) sybStack.peek()) {
                    sybStack.pop();
                    currentMax += (i - (int) intStack.pop()) + 1;
                    if(sybStack.isEmpty()){
                        maxLength = Math.max(maxLength, currentMax);
                        currentMax = 0;
                    }
                } else {
                    maxLength = Math.max(maxLength, currentMax);
                    currentMax = 0;
                }
            }
        }
        maxLength = Math.max(maxLength, currentMax);
        return maxLength;
    }

    public static void main(String[] args) {
        longestValidParentheses(")()())");
    }
}
