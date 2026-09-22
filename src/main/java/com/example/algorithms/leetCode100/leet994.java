package com.example.algorithms.leetCode100;

import java.util.*;

public class leet994 {
    public static int orangesRotting(int[][] grid) {
        List<int[]> rot1 = new ArrayList();
        List<int[]> rot2 = new ArrayList();
        int freshCount = 0;
        int rotCount = 0;
        int minu = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (1 == grid[i][j]) {
                    freshCount++;
                } else if (2 == grid[i][j]) {
                    rotCount++;
                    int[] temp = {i, j};
                    rot1.add(temp);
                }
            }
        }
        if (freshCount <= 0) {
            return 0;
        }
        if (rotCount <= 0) {
            return -1;
        }
        while (freshCount > 0 && !rot1.isEmpty()) {
            minu++;
            for (int i = 0; i < rot1.size(); i++) {
                int[] cur = rot1.get(i);
                int row = cur[0];
                int col = cur[1];
                freshCount += bfs(grid, row - 1, col, rot2);
                freshCount += bfs(grid, row + 1, col, rot2);
                freshCount += bfs(grid, row, col - 1, rot2);
                freshCount += bfs(grid, row, col + 1, rot2);
            }
            rot1.clear();
            rot1.addAll(rot2);
            rot2.clear();
        }
        return freshCount > 0 ? -1 : minu;
    }

    public static int bfs(int[][] grid, int i, int j, List<int[]> rot) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[i].length) {
            return 0;
        }
        if (1 != grid[i][j]) {
            return 0;
        }
        grid[i][j] = 2;
        int[] temp = {i, j};
        rot.add(temp);
        return -1;
    }

    public static void main(String[] args) {
//        int[][] grids = {
//                {2, 1, 1},
//                {1, 1, 0},
//                {0, 1, 1}
//        };

        int[][] grids = {
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}};
        orangesRotting(grids);
    }
}
