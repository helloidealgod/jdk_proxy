package com.example.compile.table3;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    public List<String> stack = new ArrayList<>();

    public String pop() {
        String token = null;
        if (!this.stack.isEmpty()) {
            token = stack.remove(stack.size() - 1);
        }
        return token;
    }

    public void push(String token) {
        if (token.contains(",")) {
            String[] split = token.split(",");
            for (int i = split.length - 1; i >= 0; i--) {
                stack.add(split[i]);
            }
        } else {
            stack.add(token);
        }
    }

    public String getTop() {
        String token = null;
        if (!stack.isEmpty()) {
            token = stack.get(stack.size() - 1);
        }
        return token;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        int size = 0;
        if (!stack.isEmpty()) {
            size = stack.size();
        }
        return size;
    }
}
