package com.example.compile.table3;

public class SegmentExprOp {
    public String type;
    public String op;
    public String name;
    public String value;

    public SegmentExprOp e1;
    public SegmentExprOp e2;

    public SegmentExprOp(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public SegmentExprOp(String type, String op, SegmentExprOp e1, SegmentExprOp e2) {
        this.type = type;
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }
}
