package com.example.algorithms.leetCode100;

public class Leet765 {
    public int minSwapsCouples(int[] row) {
        int len = row.length;
        int N = len / 2; // 情侣对数
        UnionFind uf = new UnionFind(N);

        // 遍历每对相邻座位，对情侣编号进行合并
        for (int i = 0; i < len; i += 2) {
            int couple1 = row[i] / 2;
            int couple2 = row[i + 1] / 2;
            uf.union(couple1, couple2);
        }

        // 最少交换次数 = 情侣总对数 - 连通分量的个数
        return N - uf.getCount();
    }

    // 并查集内部类
    class UnionFind {
        private int[] parent;
        private int count; // 连通分量个数

        public UnionFind(int n) {
            parent = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // 路径压缩
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) {
                return;
            }
            parent[rootX] = rootY;
            count--;
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) {
        int[] nums = {0, 2, 1, 3};
        Leet765 leet765 = new Leet765();
        leet765.minSwapsCouples(nums);
    }
}
