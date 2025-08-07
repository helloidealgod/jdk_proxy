package com.example.compile.table3.action;

import com.example.compile.table3.operate.SegmentExprOp;

import java.util.ArrayList;
import java.util.List;

public class Segment {
    public String expr;
    public int segmentType;
    //    public String name;
    public String type;
    public int typeWidth;
    public String varName;
    public String value;
    public Long eAddress;
    public String funName;
    public List<SegmentExprOp> funcVars = new ArrayList<>();
    public SegmentExprOp e;
    public int valStackIndex = -1;
    public int opStackIndex = -1;

    public Segment() {
    }

    public Segment(String expr) {
        this.expr = expr;
    }
}
