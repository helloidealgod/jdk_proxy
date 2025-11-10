package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.ASTNode;
import com.example.compile.aisample2.Expression;

// 变量声明
public class VariableDeclaration extends ASTNode {
    public String type;
    public String name;
    public Expression initializer;

    public VariableDeclaration(String type, String name, Expression initializer) {
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public String toString() {
        if (initializer != null) {
            return "VariableDeclaration(" + type + " " + name + " = " + initializer + ")";
        }
        return "VariableDeclaration(" + type + " " + name + ")";
    }
}

