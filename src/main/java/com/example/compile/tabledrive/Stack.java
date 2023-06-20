package com.example.compile.tabledrive;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    public List<String> toKenList = new ArrayList<>();
    public List<Integer> typeList = new ArrayList<>();
    public List<Integer> statusList = new ArrayList<>();
    public List<String> descList = new ArrayList<>();
    public int topIndex = -1;
    public int index = 0;
    public int statusTopIndex = -1;
    public int statusIndex = 0;

    public void push(String token) {
        toKenList.add(token);
        topIndex++;
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

    public void push(String token, Integer type) {
        toKenList.add(token);
        typeList.add(type);
        topIndex++;
    }

    public void push(String token, Integer type, String desc) {
        toKenList.add(token);
        typeList.add(type);
        descList.add(desc);
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

    public String peekToken() {
        String token = null;
        if (0 <= topIndex) {
            token = toKenList.get(topIndex);
        }
        return token;
    }

    public String peekDesc() {
        String token = null;
        if (0 <= topIndex) {
            token = descList.get(topIndex);
        }
        return token;
    }

    public Integer peekType() {
        Integer token = null;
        if (0 <= topIndex) {
            token = typeList.get(topIndex);
        }
        return token;
    }

    public void empty() {
        toKenList = new ArrayList<>();
        typeList = new ArrayList<>();
        descList = new ArrayList<>();
        topIndex = -1;
        index = 0;
    }

    public void print() {
        if (0 < toKenList.size()) {
            System.out.println("============start============");
            for (int i = 0; i < toKenList.size(); i++) {
                System.out.println(typeList.get(i) + " " + toKenList.get(i));
            }
            System.out.println("=============end=============");
        }
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
                System.out.print(statusList.get(i)+" ");
            }
            System.out.println("");
        }
    }

    public void printDesc() {
        if (0 < toKenList.size()) {
            System.out.println("============start============");
            for (int i = 0; i < toKenList.size(); i++) {
                System.out.println(typeList.get(i) + " " + toKenList.get(i) + " " + descList.get(i));
            }
            System.out.println("=============end=============");
        }
    }
}
