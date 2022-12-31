package com.example.compile.iteration.analysis;

import java.util.List;

public class Utils {
    public static void statementToString(List<Statement> statementList) {
        for (Statement item : statementList) {
            System.out.print(item.type + " " + item.name);
            if (null != item.initValue) {
                System.out.println(" = " + item.initValue);
            } else if (0 < item.formalParameterList.size()) {
                System.out.print("(");
                for (Statement item1 : item.getFormalParameterList()) {
                    System.out.print(item1.type + " " + item1.name + " ");
                }
                System.out.println(")");

                statementToString(item.subStatementList);
            } else {
                System.out.println("");
            }
        }
    }
}
