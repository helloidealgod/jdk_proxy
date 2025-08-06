package com.example.compile.table3.operate;

import com.example.compile.table3.operate.SegmentExprOp;

import java.util.ArrayList;
import java.util.List;

public class StackE {
    public List<SegmentExprOp> stack = new ArrayList<>();

    public SegmentExprOp pop() {
        SegmentExprOp e = null;
        if (!this.stack.isEmpty()) {
            e = stack.remove(stack.size() - 1);
        }
        return e;
    }

    public void push(SegmentExprOp e) {
        if (null != e) {
            stack.add(e);
        }
    }

    public SegmentExprOp getTop() {
        SegmentExprOp token = null;
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
