package com.example.diff;

public class Diff {
    public static void main(String[] args) {
        /**
         * 以两个字符串，src=ABCABBA，dst=CBABAC为例，
         * 根据这两个字符串我们可以构造下面一张图，横轴是src内容，纵轴是dst内容，
         * 那么图中每一条从左上角到右下角的路径，都表示一个diff。
         * 向右表示删除，向下表示新增，对角线则表示原内容保持不动
         */
        String src = "package com.example.diff;";
        String dest = "package  com.example.diff;";
        int x = 0, y = 0;
        while (true) {
            if (x < src.length() && y < dest.length()) {
                if (dest.charAt(y) == src.charAt(x)) {
                    System.out.println(dest.charAt(y));
                    x += 1;
                    y += 1;
                    //对角线
                } else {
                    System.out.println("-"+src.charAt(x));
                    x += 1;
                    //向右表示删除
                }
            }
        }
    }
}
