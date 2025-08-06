package com.example.compile.table3.action;

public class Segment {
    public String expr;
    public int segmentType;
    //    public String name;
    public String type;
    public int typeWidth;
    public String varName;

    public String value;
    public Long eAddress;

    public Segment() {
    }

    public Segment(String expr) {
        this.expr = expr;
    }
}
