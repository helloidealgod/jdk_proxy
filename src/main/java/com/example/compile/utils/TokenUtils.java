package com.example.compile.utils;

import com.example.compile.iteration.TokenStack;

import java.io.*;

public class TokenUtils {
    public PushbackReader pr;
    public int[][] stateMap = initStateMap();

    public int getIndex(char c) {
        int index = c;
        if (47 >= index && 32 <= index) {
            index -= 32;
        } else if (57 >= index && 48 <= index) {
            index = 16;
        } else if (58 <= index && 64 >= index) {
            index -= 41;
        } else if (90 >= index && 65 <= index) {
            index = 24;
        } else if (96 >= index && 91 <= index) {
            index -= 66;
        } else if (122 >= index && 97 <= index) {
            index = 31;
        } else if (126 >= index && 123 <= index) {
            index -= 91;
        } else {
            index = 0;
        }
        return index;
    }

    public int[][] initStateMap() {
        int[][] stateMap = {{-3, 4, 1, 3, -2, 16, 18, 2, 0, 0, 12, 6, 0, 9, -2, 14, 3, 0, 0, 26, 24, 30, 0, 0, 3, 0, -2, 0, -2, 3, -2, 3, 0, 21, 0, 0},
                {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {-1, -2, -2, -2, -2, -1, -1, -2, -1, -1, -1, -1, -1, -1, 3, -1, 3, -1, -1, -1, -1, -1, -1, -2, 3, -1, -2, -1, -2, 3, -2, 3, -1, -1, -1, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 5, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -1, -2, -2, -2, -2, -1, -1, -1, -2, 7, -2, -2, -2, -2, -1, -2, -2, -2, 8, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
                {-1, -2, -1, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, 10, -2, -2, -1, -2, -2, -2, 11, 34, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -1, -2, -2, -1, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -1, -1, -2, -1, -2, -2, -2, -1, -2, -2, -2, 13, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 15, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, 17, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, 19, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 20, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -1, -1, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, 23, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, 22, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -1, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, 25, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -1, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, 27, 28, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 29, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 32, 31, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2}};
        return stateMap;
    }

    public TokenStack stack = new TokenStack();

    public TokenUtils() throws FileNotFoundException {
        File f = new File("");
        BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath() + "/src/main/resources/cc/main.c"));
        pr = new PushbackReader(reader);
    }

    public String getToken() throws IOException {
        String token1 = stack.getToken();
        if (null != token1) {
            return token1;
        }
        int read;
        char c;
        StringBuilder token = new StringBuilder("");
        int status = 0;
        int index;
        while ((read = pr.read()) != -1) {
            c = (char) read;
            index = getIndex(c);
            status = stateMap[status][index];
            if (0 == status) {
                token.append(c);
                return token.toString();
            } else if (-1 == status) {
                pr.unread(c);
                return token.toString();
            } else if (-2 == status) {
                System.out.println("error:" + token.toString() + " " + c);
                break;
            } else if (-3 == status) {
                status = 0;
            } else {
                token.append(c);
            }
        }
        return null;
    }
}
