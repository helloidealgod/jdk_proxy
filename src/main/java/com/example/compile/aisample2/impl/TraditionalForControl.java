package com.example.compile.aisample2.impl;

import com.example.compile.aisample2.ASTNode;
import com.example.compile.aisample2.Expression;
import com.example.compile.aisample2.ForControl;

// 传统for循环控制
public class TraditionalForControl extends ForControl {
    public ASTNode init;
    public Expression condition;
    public ASTNode update;

    public TraditionalForControl(ASTNode init, Expression condition, ASTNode update) {
        this.init = init;
        this.condition = condition;
        this.update = update;
    }

    @Override
    public String toString() {
        return "TraditionalForControl(\n" +
                "  init=" + init + ",\n" +
                "  condition=" + condition + ",\n" +
                "  update=" + update + "\n)";
    }
}
