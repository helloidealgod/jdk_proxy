package com.example.compile.tabledrive2.impl;

import com.example.compile.tabledrive2.Analysis;
import com.example.compile.tabledrive2.Stack;

public class FunctionDeclarator extends Analysis {
    @Override
    public void analysis(Stack stack) {
        String symbol = stack.toString();
        if (symbol.endsWith(",")) {
            stack.pop();
            String keyword = stack.pop();
            String type = stack.pop();
            System.out.println("函数参数 类型：" + type + " 命名：" + keyword);
        } else if (symbol.endsWith("@KEYWORD@)")) {
            stack.pop();
            String keyword = stack.pop();
            String type = stack.pop();
            System.out.println("函数参数 类型：" + type + " 命名：" + keyword);

            stack.pop();
            String functionName = stack.pop();
            String functiontType = stack.pop();
            System.out.println("函数名：" + functionName + " 返回类型：" + functiontType);
            stack.push(functionName, "@FUNCTION");
        } else if (symbol.endsWith("@(@)")) {
            stack.pop();
            stack.pop();
            String functionName = stack.pop();
            String functiontType = stack.pop();
            System.out.println("函数名：" + functionName + " 返回类型：" + functiontType);
            stack.push(functionName, "@FUNCTION");
        }
    }
}
