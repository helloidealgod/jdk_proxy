package com.example.algorithms.leetCode100;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class leet3 {
    public static int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        int max = 0;
        int curentMax = 0;
        int tailIndex = 0;
        Map<Character, Integer> map = new HashMap();
        for (int i = 0; i < chars.length; ) {
            if (null == map.get(chars[i])) {
                curentMax = i - tailIndex + 1;
                map.put(chars[i], i);
                i++;
            } else {
                max = Math.max(max, curentMax);
                Integer remove = map.remove(chars[tailIndex]);
                tailIndex++;
                curentMax = i - tailIndex + 1;
            }
        }
        max = Math.max(max, curentMax);
        return max;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.hasNextLine();
        String string = scanner.nextLine();
        int length = lengthOfLongestSubstring(string);
        System.out.println("out:" + length);
    }
}
