package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.Expression;

import java.util.List;

// 方法调用表达式
public class MethodCall extends Expression {
    public Expression target;
    public String methodName;
    public List<Expression> arguments;

    public MethodCall(Expression target, String methodName, List<Expression> arguments) {
        this.target = target;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "MethodCall(" + target + "." + methodName + arguments + ")";
    }
}
