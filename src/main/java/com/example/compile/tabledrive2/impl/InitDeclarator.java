package com.example.compile.tabledrive2.impl;

import com.example.compile.tabledrive2.Analysis;
import com.example.compile.tabledrive2.Stack;

public class InitDeclarator extends Analysis {
    @Override
    public void analysis(Stack stack) {
        String symbol = stack.toString();
        if (symbol.endsWith(";")) {
            stack.pop();
            String value = stack.pop();
            stack.pop();
            String keyword = stack.pop();
            String type = stack.pop();
            String peek = stack.peek();
            if (!"@句子".equals(peek)) {
                stack.push("@句子", "@句子");
            }
            System.out.println("类型：" + type + " 命名：" + keyword + " 初始值：" + value);
        } else if (symbol.endsWith(",")) {
            stack.pop();
            String value = stack.pop();
            stack.pop();
            String keyword = stack.pop();
            String type = stack.pop();
            stack.push(type, "@TYPE");
            System.out.println("类型：" + type + " 命名：" + keyword + " 初始值：" + value);
        }
    }
}
