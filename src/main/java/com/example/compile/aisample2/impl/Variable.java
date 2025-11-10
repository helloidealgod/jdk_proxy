package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.Expression;

// 变量表达式
public class Variable extends Expression {
    public String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Variable(" + name + ")";
    }
}
