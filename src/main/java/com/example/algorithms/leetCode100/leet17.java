package com.example.algorithms.leetCode100;

import java.util.*;
import java.util.stream.Collectors;

public class leet17 {
    public static List<String> letterCombinations(String digits) {
        char[][] map = {
                {},
                {},
                {'a', 'b', 'c'},
                {'d', 'e', 'f'},
                {'g', 'h', 'i'},
                {'j', 'k', 'l'},
                {'m', 'n', 'o'},
                {'p', 'q', 'r', 's'},
                {'t', 'u', 'v'},
                {'w', 'x', 'y', 'z'}
        };
        char[] digit = digits.toCharArray();
        List<String> result = new ArrayList();
        Deque<Character> deque = new ArrayDeque();
        dfs(map, digit, 0, deque, result);
        return result;
    }

    public static void dfs(char[][] map, char[] chars, int n, Deque<Character> deque, List<String> result) {
        if (n >= chars.length) {
            StringBuilder sb = new StringBuilder();
            for (char c : deque) {
                sb.append(c);
            }
            result.add(sb.toString());
        } else {
            for (char c : map[chars[n] - '0']) {
                deque.offerLast(c);
                dfs(map, chars, n + 1, deque, result);
                deque.pollLast();
            }
        }
    }

    public static void main(String[] args) {
        letterCombinations("23");
    }
}
