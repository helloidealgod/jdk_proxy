package com.example.compile.table3;

import java.util.ArrayList;
import java.util.List;

public class StackE {
    public List<SegmentE> stack = new ArrayList<>();

    public SegmentE pop() {
        SegmentE e = null;
        if (!this.stack.isEmpty()) {
            e = stack.remove(stack.size() - 1);
        }
        return e;
    }

    public void push(SegmentE e) {
        if (null != e) {
            stack.add(e);
        }
    }

    public SegmentE getTop() {
        SegmentE token = null;
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
