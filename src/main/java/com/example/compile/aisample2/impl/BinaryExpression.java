package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.Expression;

// 二元运算表达式
public class BinaryExpression extends Expression {
    public Expression left;
    public String operator;
    public Expression right;

    public BinaryExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinaryExpression(" + left + " " + operator + " " + right + ")";
    }
}
