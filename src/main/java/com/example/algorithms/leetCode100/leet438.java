package com.example.algorithms.leetCode100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class leet438 {
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList();
        String targetKey = getKey(p);
        for (int i = 0; i <= s.length() - p.length(); i++) {
            String sKey = s.substring(i, i + p.length());
            if (targetKey.equals(getKey(sKey))) {
                result.add(i);
            }
        }
        return result;
    }

    public static String getKey(String s) {
        int[] key = new int[26];
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int index = charArray[i] - 'a';
            key[index] += 1;
        }
        return Arrays.toString(key);
    }

    public static void main(String[] args) {
        //findAnagrams("cbaebabacd","abc");
        findAnagrams("abab", "ab");
    }
}
