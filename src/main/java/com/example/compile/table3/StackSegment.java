package com.example.compile.table3;

import com.example.compile.table3.segment.Segment;

import java.util.ArrayList;
import java.util.List;

public class StackSegment {
    public List<Segment> stack = new ArrayList<>();

    public Segment pop() {
        Segment e = null;
        if (!this.stack.isEmpty()) {
            e = stack.remove(stack.size() - 1);
        }
        return e;
    }

    public void push(Segment e) {
        if (null != e) {
            stack.add(e);
        }
    }

    public Segment getTop() {
        Segment token = null;
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
