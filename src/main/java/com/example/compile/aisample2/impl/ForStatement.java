package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.ASTNode;
import com.example.compile.aisample2.ForControl;

// For语句节点
public class ForStatement extends ASTNode {
    public ForControl forControl;
    public ASTNode body;

    public ForStatement(ForControl forControl, ASTNode body) {
        this.forControl = forControl;
        this.body = body;
    }

    @Override
    public String toString() {
        return "ForStatement(\n" +
                "  control=" + forControl + ",\n" +
                "  body=" + body + "\n)";
    }
}
