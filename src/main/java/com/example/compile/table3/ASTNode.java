package com.example.compile.table3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 语法树节点类
class ASTNode {
    String id;
    String type;
    String value;
    List<ASTNode> children;

    public ASTNode(String type) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.children = new ArrayList<>();
    }

    public ASTNode(String type, String value) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(ASTNode child) {
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
        for (ASTNode child : children) {
            sb.append(child.toString(indent + 1));
        }
        return sb.toString();
    }
}
