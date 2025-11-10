package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.ASTNode;

import java.util.List;

// 代码块语句
public class BlockStatement extends ASTNode {
    public List<ASTNode> statements;

    public BlockStatement(List<ASTNode> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "BlockStatement" + statements;
    }
}
