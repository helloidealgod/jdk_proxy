package com.example.compile.tabledrive;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    public List<String> toKenList = new ArrayList<>();
    public List<Integer> statusList = new ArrayList<>();
    public int topIndex = -1;
    public int statusTopIndex = -1;

    public void push(String token) {
        toKenList.add(token);
        topIndex++;
    }

    public String pop() {
        String token = null;
        if (0 <= topIndex) {
            token = toKenList.get(topIndex);
            toKenList.remove(topIndex);
            topIndex--;
        }
        return token;
    }

    public void pushStatus(Integer status) {
        statusList.add(status);
        statusTopIndex++;
    }

    public Integer popStatus() {
        Integer token = null;
        if (0 <= statusTopIndex) {
            token = statusList.get(statusTopIndex);
            statusList.remove(statusTopIndex);
            statusTopIndex--;
        }
        return token;
    }

    public void empty() {
        toKenList = new ArrayList<>();
        topIndex = -1;
    }

    public void printToken() {
        if (0 < toKenList.size()) {
            System.out.print("栈内容：");
            for (int i = 0; i < toKenList.size(); i++) {
                System.out.print(toKenList.get(i));
            }
            System.out.println("");
        }
    }

    public void printStatus() {
        if (0 < statusList.size()) {
            System.out.print("状态栈内容：");
            for (int i = 0; i < statusList.size(); i++) {
                System.out.print(statusList.get(i) + " ");
            }
            System.out.println("");
        }
    }

    public Integer peekStatus() {
        Integer token = null;
        if (0 <= statusTopIndex) {
            token = statusList.get(statusTopIndex);
        }
        return token;
    }
}
