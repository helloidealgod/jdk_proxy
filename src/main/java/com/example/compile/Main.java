package com.example.compile;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = (new BufferedReader(new FileReader("/Users/jiangxiankun/workspace_c/cc/main.c")));
            PushbackReader pr = new PushbackReader(reader);
            int read;
            char c;
            StringBuilder token = new StringBuilder("");
            int status = 0;
            // 0-9 a-z A-Z
            // +-*/ % = ! <> [] {} . & () ; , \ ' "
            // 空白符
            // 单词、数字、常量（字符，字符串，数字）、分隔符、运算符
            while ((read = pr.read()) != -1) {
                c = (char) read;
                if ('a' <= c && 'z' >= c || 'A' <= c && 'Z' >= c || '0' <= c && '9' >= c || '_' == c || '#' == c || '.' == c) {
                    token.append(c);
                } else if ('(' == c || ')' == c || '>' == c || '<' == c || '{' == c || '}' == c || ';' == c || ',' == c) {
                    if (0 < token.length() && 0 == status) {
                        pr.unread(read);
                    } else {
                        token.append(c);
                    }
                    tokenProcess(token);
                } else if ('"' == c) {
                    if (0 == status) {
                        status = 1;
                    } else {
                        status = 0;
                    }
                    token.append(c);
                } else if ('\'' == c) {
                    if (0 == status) {
                        status = 2;
                    } else {
                        status = 0;
                    }
                    token.append(c);
                } else if (' ' == c) {
                    if (0 == status) {
                        if (0 < token.length()) {
                            tokenProcess(token);
                        }
                    } else {
                        token.append(c);
                    }
                }
            }
            pr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tokenProcess(StringBuilder token) {
        System.out.println(token.toString());
        token.delete(0, token.length());
    }
}
