package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.ASTNode;
import com.example.compile.aisample2.Expression;

import java.util.List;

// 表达式列表
class ExpressionList extends ASTNode {
    public List<Expression> expressions;

    public ExpressionList(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public String toString() {
        return "ExpressionList" + expressions;
    }
}
