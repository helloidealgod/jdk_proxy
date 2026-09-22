package com.example.algorithms.leetCode100;

import java.util.*;

public class leet49 {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> result = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            String key = getKey(strs[i]);
            List<String> strings = result.get(key);
            if (null == strings) {
                strings = new ArrayList<>();
            }
            strings.add(strs[i]);
            result.put(key, strings);
        }
        List<List<String>> re = new ArrayList<>();
        Set<Map.Entry<String, List<String>>> entries = result.entrySet();
        for (Map.Entry<String, List<String>> item : entries) {
            List<String> value = item.getValue();
            re.add(value);
        }
        return re;
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
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        groupAnagrams(strs);
    }
}
