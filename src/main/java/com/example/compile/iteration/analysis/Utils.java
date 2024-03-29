package com.example.compile.iteration.analysis;

import java.util.List;
import java.util.Stack;

import static com.example.compile.iteration.analysis.Operation.*;
import static com.example.compile.iteration.analysis.StatementType.ZKH;

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

    public static void resultListToString(List<Result> resultList, int layer) {
        for (Result result : resultList) {

            int statementType = result.statementType;
            switch (statementType) {
                case 0:
                    System.out.println("error");
                    break;
                case 1:
                    Result result1 = null;
                    if (0 < result.operateList.size()) {
                        for (int i = 0; i < result.operateList.size() - 1; i++) {
                            printTab(layer);
                            result1 = result.operateList.get(i);
                            System.out.print(result1.operationTypeStr + " ");
                            if (StatementType.CONSTANT.getValue() == result1.operation1.statementType) {
                                System.out.print(result1.operation1.initValue + " ");
                            } else {
                                System.out.print(result1.operation1.name + " ");
                            }
                            if (StatementType.CONSTANT.getValue() == result1.operation2.statementType) {
                                System.out.println(result1.operation2.initValue);
                            } else {
                                System.out.println(result1.operation2.name);
                            }
                        }
                    }
                    printTab(layer);
                    System.out.print(result.typeStr);
                    System.out.print(" ");
                    System.out.print(result.width);
                    System.out.print(" ");
                    if (result.type == DataType.PTR.getValue()) {
                        printPtr(result);
                    }
                    System.out.print(result.name);
                    if (0 < result.operateList.size()) {
                        System.out.print(" = ");
                        result1 = result.operateList.get(result.operateList.size() - 1);
                        System.out.print(result1.operationTypeStr + " ");
                        if (StatementType.CONSTANT.getValue() == result1.operation1.statementType) {
                            System.out.print(result1.operation1.initValue + " ");
                        } else {
                            System.out.print(result1.operation1.name + " ");
                        }
                        if (StatementType.CONSTANT.getValue() == result1.operation2.statementType) {
                            System.out.print(result1.operation2.initValue);
                        } else {
                            System.out.print(result1.operation2.name);
                        }
                    }
                    System.out.println("");
                    break;
                case 2:
                    System.out.print(result.name);
                    System.out.print(" = ");
                    if (null != result.rel.statementTypeStr) {
                        System.out.print(result.rel.statementTypeStr + " ");
                    }
                    System.out.println(result.rel.name);
                    break;
                case 3:
                case 4:
                    System.out.print(result.typeStr);
                    System.out.print(" ");
                    System.out.print(result.width);
                    System.out.print(" ");
                    System.out.print(result.name);
                    System.out.print("(");
                    for (Result item : result.formalParameterList) {
                        System.out.print(item.typeStr + " " + item.name + ",");
                    }
                    System.out.println(")");
                    if (0 < result.subList.size()) {
                        System.out.println("{");
                        resultListToString(result.subList, layer + 1);
                        for (int i = 0; i < layer; i++) {
                            System.out.print("  ");
                        }
                        System.out.println("}");
                    }
                    break;
                case 5:
                    System.out.println("");
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    printTab(layer);
                    System.out.print(result.name);
                    System.out.println("{");
                    if (0 < result.subList.size()) {
                        resultListToString(result.subList, layer + 1);
                    }
                    for (int i = 0; i < layer; i++) {
                        System.out.print("  ");
                    }
                    System.out.println("}");
                    break;
                case 11:
                case 12:
                    printTab(layer);
                    System.out.println(result.name);
                    break;
                default:
                    System.out.println("");
                    break;
            }
        }
    }

    private static void printPtr(Result result) {
        if (null != result.rel) {
            printPtr(result.rel);
        }
        if (result.type == DataType.PTR.getValue()) {
            System.out.print("*");
        } else {
            System.out.print(result.typeStr + " ");
        }
    }

    private static void printTab(int layer) {
        for (int i = 0; i < layer; i++) {
            System.out.print("  ");
        }
    }

    public static boolean compareOperate(Stack<String> operateStack, String operate) {
        if (operateStack.isEmpty()) {
            return false;
        }
        String peek = operateStack.peek();
        if (PLUS.name().equals(peek) || SUB.name().equals(peek)) {
            if (MUL.name().equals(operate) || DIV.name().equals(operate) || MOD.name().equals(operate)) {
                //优先级高 入栈
                return false;
            } else {
                //优先级相等 运算之前的
                return true;
            }
        }else if(ZKH.name().equals(peek)){
            return false;
        }
        return true;
    }
}
