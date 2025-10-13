package com.example.compile.table3.stack;


import com.example.compile.table3.tree.action.Action;

import java.util.ArrayList;
import java.util.List;

public class ActionStack {
    public List<Action> stack = new ArrayList<>();

    public Action pop() {
        Action token = null;
        if (!this.stack.isEmpty()) {
            token = stack.remove(stack.size() - 1);
        }
        return token;
    }

    public void push(Action token) {
        stack.add(token);
    }

    public Action getTop() {
        Action token = null;
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
