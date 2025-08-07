package com.example.compile.table3.operate;

import com.example.compile.table3.middle.Result;

import java.util.ArrayList;
import java.util.List;

public class StackE {
    public List<Result> stack = new ArrayList<>();

    public Result pop() {
        Result e = null;
        if (!this.stack.isEmpty()) {
            e = stack.remove(stack.size() - 1);
        }
        return e;
    }

    public void push(Result e) {
        if (null != e) {
            stack.add(e);
        }
    }

    public Result getTop() {
        Result token = null;
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
