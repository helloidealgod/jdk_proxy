package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.Expression;
import com.example.compile.aisample2.ForControl;

// 增强for循环控制
class EnhancedForControl extends ForControl {
    public VariableDeclaration variable;
    public Expression collection;

    public EnhancedForControl(VariableDeclaration variable, Expression collection) {
        this.variable = variable;
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "EnhancedForControl(\n" +
                "  variable=" + variable + ",\n" +
                "  collection=" + collection + "\n)";
    }
}
