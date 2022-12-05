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
            int[][] stateMap = new int[2][2];
            int status = 0;
            // 0-9 a-z A-Z
            // +-*/ % = ! <> [] {} . & () ; , \ ' "
            // 空白符
            // 单词、数字、常量（字符，字符串，数字）、分隔符、运算符
            while ((read = pr.read()) != -1) {
                c = (char) read;
                if ('a' <= c && 'z' >= c || 'A' <= c && 'Z' >= c || '0' <= c && '9' >= c || '_' == c || '#' == c || '.' == c) {
                    token.append(c);
                } else if ('(' == c || ')' == c || '>' == c || '<' == c || '{' == c || '}' == c || ';' == c || ',' == c || '[' == c || ']' == c || '~' == c) {
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
                } else if (' ' == c || '\n' == c) {
                    if (0 == status) {
                        if (0 < token.length()) {
                            tokenProcess(token);
                        }
                    } else {
                        token.append(c);
                    }
                } else if ('+' ==c ){
                    if(0 == status){
                        status = 3;
                        token.append(c);
                    }else if(3 == status){
                        token.append(c);
                        tokenProcess(token);
                        status = 0;
                    }
                }else if ('-' ==c ){
                    if(0 == status){
                        status = 4;
                        token.append(c);
                    }else if(4 == status){
                        token.append(c);
                        tokenProcess(token);
                        status = 0;
                    }
                }else if ('*' ==c ){
                    if(0 == status){
                        status = 5;
                        token.append(c);
                    }else if(5 == status){
                        token.append(c);
                        tokenProcess(token);
                        status = 0;
                    }
                }else if ('/' ==c ){
                    if(0 == status){
                        status = 6;
                        token.append(c);
                    }else if(6 == status){
                        token.append(c);
                        tokenProcess(token);
                        status = 0;
                    }
                }else if ('%' ==c ){
                    if(0 == status){
                        status = 7;
                        token.append(c);
                    }else if(7 == status){
                        token.append(c);
                        tokenProcess(token);
                        status = 0;
                    }
                }else if ('&' ==c ){
                    if(0 == status){
                        status = 8;
                        token.append(c);
                    }else if(8 == status){
                        token.append(c);
                        tokenProcess(token);
                        status = 0;
                    }
                }else if ('|' ==c ){
                    if(0 == status){
                        status = 9;
                        token.append(c);
                    }else if(9 == status){
                        token.append(c);
                        tokenProcess(token);
                        status = 0;
                    }
                }else if ('!' ==c ){
                    if(0 == status){
                        status = 10;
                        token.append(c);
                    }else if(10 == status){
                        pr.unread(c);
                        tokenProcess(token);
                        status = 0;
                    }
                }else if ('=' ==c ){
                    if(0 == status || 3 <= status && 10 >= status){
                        status = 11;
                        token.append(c);
                    }else if(11 == status){
                        token.append(c);
                        tokenProcess(token);
                        status = 0;
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
