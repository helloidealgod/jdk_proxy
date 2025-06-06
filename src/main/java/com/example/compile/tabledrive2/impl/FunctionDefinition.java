package com.example.compile.tabledrive2.impl;

import com.example.compile.tabledrive2.Analysis;
import com.example.compile.tabledrive2.Stack;

public class FunctionDefinition extends Analysis {
    @Override
    public void analysis(Stack stack) {
        String symbol = stack.toString();
        if (symbol.endsWith(";")) {
            stack.pop();
            String functionName = stack.pop();
            System.out.println("函数声明：" + functionName);
        } else if (symbol.endsWith("}")) {

        }
    }
}
