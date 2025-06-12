package com.example.compile.table3.segment;

public abstract class Segment {
    public String type;
    public String name;

    public abstract int execute(String token);
}
