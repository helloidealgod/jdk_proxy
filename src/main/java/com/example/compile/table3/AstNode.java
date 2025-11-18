package com.example.compile.table3;

import java.util.ArrayList;
import java.util.List;

// 语法树节点类
class AstNode {
    String type;
    String value;
    List<AstNode> children;

    public AstNode(String type) {
        this.type = type;
        this.children = new ArrayList<>();
    }

    public AstNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(AstNode child) {
        if (child != null) {
            children.add(child);
        }
    }

    @Override
    public String toString() {
        return toString(0);
    }

    private String toString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        sb.append(type);
        if (value != null && !value.isEmpty()) {
            sb.append(": ").append(value);
        }
        sb.append("\n");
        for (AstNode child : children) {
            sb.append(child.toString(indent + 1));
        }
        return sb.toString();
    }
}
