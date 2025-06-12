package com.example.compile.table3.segment.impl;

import com.example.compile.table3.segment.Segment;

public class SegTypeCall extends Segment {

    public SegTypeCall(String name) {
        this.name = name;
        this.type = "type_call";
    }

    @Override
    public int execute(String token) {
        return 0;
    }
}
