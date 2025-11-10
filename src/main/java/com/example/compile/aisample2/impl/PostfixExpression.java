package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.Expression;

// 后置自增表达式
public class PostfixExpression extends Expression {
    public Variable variable;
    public String operator;

    public PostfixExpression(Variable variable, String operator) {
        this.variable = variable;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "PostfixExpression(" + variable + operator + ")";
    }
}