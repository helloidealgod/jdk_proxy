package com.example.compile;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    public List<String> toKenList = new ArrayList<>();
    public List<String> failToKenList;
    public int topIndex = -1;
    public int index = 0;

    public void push(String token) {
        toKenList.add(token);
        topIndex++;
    }

    public String pop() {
        String token = null;
        if (0 <= topIndex) {
            token = toKenList.get(topIndex);
            topIndex--;
        }
        return token;
    }

    public String peek() {
        String token = null;
        if (0 <= topIndex) {
            token = toKenList.get(topIndex);
        }
        return token;
    }

    public void empty() {
        toKenList = new ArrayList<>();
        failToKenList = new ArrayList<>();
        topIndex = -1;
        index = 0;
    }

    public void print() {
        if (0 < toKenList.size()) {
            toKenList.forEach(item->{
                System.out.print(item+" ");
            });
            System.out.println("");
            this.empty();
        }
    }

    public void failed(){
        failToKenList = toKenList;
        toKenList = new ArrayList<>();
    }

    public String getToken() {
        if (null != failToKenList && index < failToKenList.size() && failToKenList.size() > 0) {
            String s = failToKenList.get(index);
            index++;
            return s;
        } else {
            failToKenList = new ArrayList<>();
        }
        return null;
    }
}
