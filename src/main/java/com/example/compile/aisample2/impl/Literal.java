package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.Expression;

// 字面量表达式
public class Literal extends Expression {
    public Object value;
    public String type;

    public Literal(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Literal(" + value + " : " + type + ")";
    }
}
