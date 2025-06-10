package com.example.compile.table3;

public class SegmentE {
    public String type;
    public String op;
    public String name;
    public String value;

    public SegmentE e1;
    public SegmentE e2;

    public SegmentE(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public SegmentE(String type, String op, SegmentE e1, SegmentE e2) {
        this.type = type;
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }
}
