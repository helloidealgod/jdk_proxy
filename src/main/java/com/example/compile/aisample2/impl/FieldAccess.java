package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.Expression;

// 字段访问表达式
public class FieldAccess extends Expression {
    public Expression target;
    public String fieldName;

    public FieldAccess(Expression target, String fieldName) {
        this.target = target;
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "FieldAccess(" + target + "." + fieldName + ")";
    }
}