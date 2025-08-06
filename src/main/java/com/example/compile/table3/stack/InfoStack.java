package com.example.compile.table3.stack;

import com.example.compile.table3.action.Segment;

import java.util.ArrayList;
import java.util.List;

public class InfoStack {
    public List<Segment> stack = new ArrayList<>();

    public Segment pop() {
        Segment token = null;
        if (!this.stack.isEmpty()) {
            token = stack.remove(stack.size() - 1);
        }
        return token;
    }

    public void push(String token) {
        if (token.contains(",") && !token.equals(",")) {
            String[] split = token.split(",");
            for (int i = split.length - 1; i >= 0; i--) {
                String comma = split[i].replaceAll("comma", ",");
                comma = comma.replaceAll("semi", ";");
                Segment segment = new Segment(comma);
                stack.add(segment);
            }
        } else {
            String comma = token.replaceAll("comma", ",");
            comma = comma.replaceAll("semi", ";");
            Segment segment = new Segment(comma);
            stack.add(segment);
        }
    }

    public void pushCommand(String command) {
        Segment segment = new Segment(command);
        stack.add(segment);
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

    public void print() {
        for (Segment segment : stack) {
            System.out.print(segment.expr + " ");
        }
        System.out.println("");
    }
}
