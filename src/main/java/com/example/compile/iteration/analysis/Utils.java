package com.example.compile.iteration.analysis;

import java.util.List;

public class Utils {
    public static void statementToString(List<Statement> statementList, int layer) {
        for (Statement item : statementList) {
            for (int i = 0; i < layer; i++) {
                System.out.print("  ");
            }
            System.out.print(item.type + " " + item.name);
            if (null != item.initValue) {
                System.out.println(" = " + item.initValue);
            } else if (0 < item.formalParameterList.size()) {
                System.out.print("(");
                for (Statement item1 : item.getFormalParameterList()) {
                    System.out.print(item1.type + " " + item1.name + " ");
                }
                System.out.print(")");
            } else {
                System.out.println("");
            }
            if (0 < item.subStatementList.size()) {
                System.out.println("{");
                statementToString(item.subStatementList, layer + 1);
                System.out.println("}");
            }
        }
    }
}
