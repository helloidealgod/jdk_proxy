package com.example.compile.table3.segment.impl;

import com.example.compile.table3.segment.Segment;

import java.util.List;

public class SegTypeFunCall extends Segment {
    public List<SegTypeExpr> EList;

    public SegTypeFunCall(String name) {
        this.name = name;
        this.type = "funCall";
    }

    @Override
    public int execute(String token) {
        return 0;
    }
}
